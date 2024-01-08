package com.zizo.carteeng.members;

import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() { return memberRepository.findAll(); }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("not found member"));
        return member;
    }

    //action에 따라 member, partner의 status, partner 변경
    @Transactional
   public void updateStatusByAction(String action, Member member, Member partner) {
        if (action.equals("request")) { //나 얘 맘에들어 신청
            if (partner.getStatus() == MemberStatus.AVAILABLE) {
                member.updateMemberStatus(MemberStatus.REQUESTING);
                partner.updateMemberStatus(MemberStatus.RESPONDING);
                partner.updateMemberPartner(member);
                member.updateMemberPartner(partner);
            } else {
                //에러에러 가능하지 않다!!
            }
        } else if (action.equals("reject")) { //거절 or 취소
            member.updateMemberStatus(MemberStatus.AVAILABLE);
            partner.updateMemberStatus(MemberStatus.AVAILABLE);
            member.updateMemberPartner(null);
            partner.updateMemberPartner(null);

        } else if (action.equals("accept")) { //수락
            if (member.getPartner() == partner) {
                member.updateMemberStatus(MemberStatus.MATCHED);
                member.updateMemberStatus(MemberStatus.MATCHED);
            }

        }
    }
}
