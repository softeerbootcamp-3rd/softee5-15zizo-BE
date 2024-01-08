package com.zizo.carteeng.members;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.members.dto.MemberStatusAction;
import com.zizo.carteeng.members.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zizo.carteeng.members.model.MemberStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() { return memberRepository.findAll(); }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ErrorException(ErrorCode.MEMBER_NOT_FOUND));
        return member;
    }

    //action에 따라 member, partner의 status, partner 변경
    public void updateStatusByAction(MemberStatusAction action, Long memberId, Long partnerId) {

        if(memberId == partnerId)
            throw new ErrorException(ErrorCode.MATCH_MYSELF);

        Member member = findById(memberId);
        Member partner = findById(partnerId);

        if (action == MemberStatusAction.REQUEST) //나 얘 맘에들어 신청
            updateStatusByRequest(member, partner);
        else if (action == MemberStatusAction.REJECT) //거절 or 취소
            updateStatusByReject(member, partner);
        else if (action == MemberStatusAction.ACCEPT) //수락
            updateStatusByAccept(member, partner);
    }

    private void updateStatusByRequest(Member member, Member partner) {
        if(!(member.getStatus() == AVAILABLE && partner.getStatus() == AVAILABLE))
            throw new ErrorException(ErrorCode.MEMBER_NOT_AVAILABLE);

        member.updateMemberStatus(REQUESTING);
        partner.updateMemberStatus(RESPONDING);
        partner.updateMemberPartner(member);
        member.updateMemberPartner(partner);
    }

    private void updateStatusByAccept(Member member, Member partner) {
        if(member.getPartner() != partner || partner.getPartner() != member)
            throw new ErrorException(ErrorCode.NOT_PARTNER);

        if(!(member.getStatus() == RESPONDING && partner.getStatus() == REQUESTING))
            throw new ErrorException(ErrorCode.MEMBER_NOT_ACCEPT);

        member.updateMemberStatus(MATCHED);
        partner.updateMemberStatus(MATCHED);
    }

    private void updateStatusByReject(Member member, Member partner) {
        if(member.getPartner() != partner || partner.getPartner() != member)
            throw new ErrorException(ErrorCode.NOT_PARTNER);

        if(!((member.getStatus() == RESPONDING && partner.getStatus() == REQUESTING)
            || (member.getStatus() == REQUESTING && partner.getStatus() == RESPONDING)))
            throw new ErrorException(ErrorCode.MEMBER_NOT_REJECT);

        member.updateMemberStatus(AVAILABLE);
        partner.updateMemberStatus(AVAILABLE);

        member.updateMemberPartner(null);
        partner.updateMemberPartner(null);
    }
}
