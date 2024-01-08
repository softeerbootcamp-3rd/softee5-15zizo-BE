package com.zizo.carteeng.members;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.matches.MatchRepository;
import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.dto.MemberStatusAction;
import com.zizo.carteeng.members.model.Member;
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

    public Member updateStatusByAction(MemberStatusAction action, Long memberId, Long partnerId) {

        if(memberId == partnerId)
            throw new ErrorException(ErrorCode.CANNOT_MATCH_BY_MYSELF);

        Member member = findById(memberId);
        Member partner = findById(partnerId);

        action.apply(member, partner);

        if(action == MemberStatusAction.MEET) {
            Member driver = member.getHasCar() ? member : partner;

            Match match = Match.builder()
                    .driver(driver)
                    .walker(driver.getPartner())
                    .build();

            matchRepository.save(match);
        }

        return member;
    }
}
