package com.zizo.carteeng.matches;

import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.MemberService;
import com.zizo.carteeng.members.dto.MatchResDto;
import com.zizo.carteeng.members.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MatchController {

    private final MemberService memberService;
    private final MatchService matchService;

    //탑승 완료 버튼 클릭시 요청
    @PostMapping("/matches/{memberId}")
    public ResponseEntity<MatchResDto> createMatch(@PathVariable long memberId) { //아이디 뭐로 받을지 결정해야함

        Member member = memberService.findById(memberId);
        Match match = matchService.createMatch(member); //매치 생성

        MatchResDto matchResDto = MatchResDto.builder()
                .driver(match.getDriver())
                .walker(match.getWalker())
                .createdAt(match.getCreatedAt())
                .build();
        return ResponseEntity.ok().body(matchResDto); //만약 상대 세션이 끊어졌다면??
    }

}
