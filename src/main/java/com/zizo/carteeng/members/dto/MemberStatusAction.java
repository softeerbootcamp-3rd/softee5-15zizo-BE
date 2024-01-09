package com.zizo.carteeng.members.dto;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;

import java.util.function.BiFunction;

public enum MemberStatusAction {
    REQUEST(((member, partner) -> {
        if((member.getHasCar() && partner.getHasCar()) || !(member.getHasCar() || partner.getHasCar()))
            throw new ErrorException(ErrorCode.CANNOT_MATCH);

        if(!(member.getStatus() == MemberStatus.AVAILABLE && partner.getStatus() == MemberStatus.AVAILABLE))
            throw new ErrorException(ErrorCode.ALREADY_MATCHED);

        member.updateMemberStatus(MemberStatus.REQUESTING);
        partner.updateMemberStatus(MemberStatus.RESPONDING);
        member.updateMemberPartner(partner);
        partner.updateMemberPartner(member);
        return member;
    })),
    ACCEPT(((member, partner) -> {
        if(!member.isPartner(partner))
            throw new ErrorException(ErrorCode.NOT_THE_PARTNER);

        if(!(member.getStatus() == MemberStatus.RESPONDING && partner.getStatus() == MemberStatus.REQUESTING))
            throw new ErrorException(ErrorCode.CANNOT_ACCEPT);

        if((member.getHasCar() && partner.getHasCar()) || !(member.getHasCar() || partner.getHasCar()))
            throw new ErrorException(ErrorCode.CANNOT_MATCH);

        member.updateMemberStatus(MemberStatus.MATCHED);
        partner.updateMemberStatus(MemberStatus.MATCHED);
        return member;
    })),
    REJECT(((member, partner) -> {
        if(!member.isPartner(partner))
            throw new ErrorException(ErrorCode.NOT_THE_PARTNER);

        if(!((member.getStatus() == MemberStatus.RESPONDING && partner.getStatus() == MemberStatus.REQUESTING)
            || (member.getStatus() == MemberStatus.REQUESTING && partner.getStatus() == MemberStatus.RESPONDING)))
            throw new ErrorException(ErrorCode.CANNOT_REJECT);

        if((member.getHasCar() && partner.getHasCar()) || !(member.getHasCar() || partner.getHasCar()))
            throw new ErrorException(ErrorCode.CANNOT_MATCH);

        member.updateMemberStatus(MemberStatus.AVAILABLE);
        partner.updateMemberStatus(MemberStatus.AVAILABLE);
        member.updateMemberPartner(null);
        partner.updateMemberPartner(null);
        return member;
    })),
    MEET(((member, partner) -> {
        if(!member.isPartner(partner))
            throw new ErrorException(ErrorCode.NOT_THE_PARTNER);

        if(!(member.getStatus() == MemberStatus.MATCHED && partner.getStatus() == MemberStatus.MATCHED))
            throw new ErrorException(ErrorCode.CANNOT_MEET);

        if((member.getHasCar() && partner.getHasCar()) || !(member.getHasCar() || partner.getHasCar()))
            throw new ErrorException(ErrorCode.CANNOT_MATCH);

        member.updateMemberStatus(MemberStatus.MEET);
        partner.updateMemberStatus(MemberStatus.MEET);
        return member;
    })),
    RESTART(((member, partner) -> { // TODO: restart가 필요한가?? : MEET -> Match record 생성, member, partner 초기화 : LOGOUT을 만들어야 한다.
        if(!(member.getStatus() == MemberStatus.MEET && partner.getStatus() == MemberStatus.MEET))
            throw new ErrorException(ErrorCode.CANNOT_RESTART);

        member.updateMemberStatus(MemberStatus.AVAILABLE);
        partner.updateMemberStatus(MemberStatus.AVAILABLE);
        partner.updateMemberPartner(null);
        member.updateMemberPartner(null);
        return member;
    }));

    private BiFunction<Member, Member, Member> updateStatus;

    MemberStatusAction(BiFunction<Member, Member, Member> updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Member apply(Member member, Member partner) {
        return updateStatus.apply(member, partner);
    }
}
