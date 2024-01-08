package com.zizo.carteeng.members;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.matches.MatchRepository;
import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.dto.MemberStatusAction;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() { return memberRepository.findAll(); }

    public List<Member> getAllMembersByHasCar(Boolean hasCar) { return memberRepository.findByHasCar(hasCar); }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ErrorException(ErrorCode.MEMBER_NOT_FOUND));
        return member;
    }

    //action에 따라 member, partner의 status, partner 변경
    public Member updateStatusByAction(MemberStatusAction action, Long memberId, Long partnerId) {

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
        else if (action == MemberStatusAction.MEET)
            updateStatusByMeet(member, partner);
        else if (action == MemberStatusAction.RESTART)
            updateStatusByRestart(member, partner);

        return member;
    }

    private void updateStatusByRequest(Member member, Member partner) {
        if(!(member.getStatus() == MemberStatus.AVAILABLE && partner.getStatus() == MemberStatus.AVAILABLE))
            throw new ErrorException(ErrorCode.MEMBER_NOT_AVAILABLE);

        member.updateMemberStatus(MemberStatus.REQUESTING);
        partner.updateMemberStatus(MemberStatus.RESPONDING);
        partner.updateMemberPartner(member);
        member.updateMemberPartner(partner);
    }

    private void updateStatusByAccept(Member member, Member partner) {
        if(member.getPartner() != partner || partner.getPartner() != member)
            throw new ErrorException(ErrorCode.NOT_PARTNER);

        if(!(member.getStatus() == MemberStatus.RESPONDING && partner.getStatus() == MemberStatus.REQUESTING))
            throw new ErrorException(ErrorCode.MEMBER_NOT_ACCEPT);

        member.updateMemberStatus(MemberStatus.MATCHED);
        partner.updateMemberStatus(MemberStatus.MATCHED);
    }

    private void updateStatusByReject(Member member, Member partner) {
        if(member.getPartner() != partner || partner.getPartner() != member)
            throw new ErrorException(ErrorCode.NOT_PARTNER);

        if(!((member.getStatus() == MemberStatus.RESPONDING && partner.getStatus() == MemberStatus.REQUESTING)
            || (member.getStatus() == MemberStatus.REQUESTING && partner.getStatus() == MemberStatus.RESPONDING)))
            throw new ErrorException(ErrorCode.MEMBER_NOT_REJECT);

        member.updateMemberStatus(MemberStatus.AVAILABLE);
        partner.updateMemberStatus(MemberStatus.AVAILABLE);
        member.updateMemberPartner(null);
        partner.updateMemberPartner(null);
    }

    private Member updateStatusByMeet(Member member, Member partner) {
        if(member.getPartner() != partner || partner.getPartner() != member)
            throw new ErrorException(ErrorCode.NOT_PARTNER);

        if(!(member.getStatus() == MemberStatus.MATCHED && partner.getStatus() == MemberStatus.MATCHED))
            throw new ErrorException(ErrorCode.NOT_PARTNER);

        Member driver, walker;
        if (member.getHasCar() && !partner.getHasCar()) {
            driver = member;
            walker = partner;
        }
        else if (!member.getHasCar() && partner.getHasCar()) {
            driver = partner;
            walker = member;
        }
        else
            throw new ErrorException(ErrorCode.CANNOT_MATCH);

        walker.updateMemberStatus(MemberStatus.MEET);
        driver.updateMemberStatus(MemberStatus.MEET);

        Match match = Match.builder()
                .driver(driver)
                .walker(walker)
                .build();

        matchRepository.save(match);

        return member;
    }

    private Member updateStatusByRestart(Member member, Member partner) {
        if(!(member.getStatus() == MemberStatus.MEET && partner.getStatus() == MemberStatus.MEET))
            throw new ErrorException(ErrorCode.NOT_MEET);

        member.updateMemberStatus(MemberStatus.AVAILABLE);
        partner.updateMemberStatus(MemberStatus.AVAILABLE);
        partner.updateMemberPartner(null);
        member.updateMemberPartner(null);
        return member;
    }
}
