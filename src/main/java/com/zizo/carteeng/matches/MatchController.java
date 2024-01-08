package com.zizo.carteeng.matches;

import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.dto.MatchResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/{partnerId}")
    public ResponseEntity<MatchResDto> createMatch(@PathVariable long partnerId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member_id");

        Match match = matchService.createMatch(memberId, partnerId); // 매치 생성

        MatchResDto matchResDto = MatchResDto.builder()
                .driver(match.getDriver())
                .walker(match.getWalker())
                .createdAt(match.getCreatedAt())
                .build();

        return ResponseEntity.ok().body(matchResDto);
    }

}
