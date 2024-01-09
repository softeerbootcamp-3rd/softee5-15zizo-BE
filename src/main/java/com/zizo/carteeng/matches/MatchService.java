package com.zizo.carteeng.matches;


import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public Match createMatch(Member member, Member partner) {

        if(!member.isPartner(partner))
            throw new ErrorException(ErrorCode.NOT_THE_PARTNER);

        if(!(member.getStatus() == MemberStatus.MEET && member.getStatus() == MemberStatus.MEET))
            throw new ErrorException(ErrorCode.NOT_MEET);

        Member driver = member.getHasCar() ? member : partner;

        Match match = Match.builder()
                .driver(driver)
                .walker(driver.getPartner())
                .build();

        return matchRepository.save(match);

    }
}