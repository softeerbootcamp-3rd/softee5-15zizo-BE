package com.zizo.carteeng.matches;

import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.MemberRepository;
import com.zizo.carteeng.members.MemberService;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public Match createMatch(Member member, Member partner) {
        Member driver, walker;

        if (member.getHasCar()) {
            driver = member;
            walker = partner;
        } else {
            driver = partner;
            walker = member;
        }

        // 둘 다 탑승완료로 상태 변경
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