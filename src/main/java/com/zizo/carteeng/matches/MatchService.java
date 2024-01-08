package com.zizo.carteeng.matches;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.MemberService;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MemberService memberService;

    public Match createMatch(Long memberId, Long partnerId) {
        Member member = memberService.findById(memberId);

        if(member.getId() == partnerId)
            throw new ErrorException(ErrorCode.MATCH_MYSELF);

        Member partner = memberService.findById(partnerId);

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

        return match;
    }
}
/**
 * 둘다 MATCHED 상태
 * 한명이 탑승완료 누름
 * 매치에 저장됨
 * 매치에 저장됐음을 탑승완료 누른애는 알수있는데
 * 매치 상대방에게도 알려줘야함 어떻게 알려주지?
 * -> MEET 상태 추가 둘다 MEET로 변경
 */