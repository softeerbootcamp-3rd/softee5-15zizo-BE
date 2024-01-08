package com.zizo.carteeng.matches;

import com.zizo.carteeng.matches.domain.Match;
import com.zizo.carteeng.members.MemberService;
import com.zizo.carteeng.members.dto.MatchResDto;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MatchController {

    private final MemberService memberService;
    private final MatchService matchService;

    @PostMapping("/matches/{partnerId}")
    public ResponseEntity<MatchResDto> createMatch(@PathVariable long partnerId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member_id");

        if(memberId == partnerId)
            return ResponseEntity.badRequest().body(null); // TODO: Error Response

        Member member = memberService.findById(memberId);
        Member partner = memberService.findById(partnerId);

        if(member.getPartner() != partner || partner.getPartner() != member)
            return ResponseEntity.badRequest().body(null); // TODO: Error Response

        if(!(member.getStatus() == MemberStatus.MATCHED && partner.getStatus() == MemberStatus.MATCHED))
            return ResponseEntity.badRequest().body(null); // TODO: Error Response

        Match match = matchService.createMatch(member, partner); // 매치 생성

        MatchResDto matchResDto = MatchResDto.builder()
                .driver(match.getDriver())
                .walker(match.getWalker())
                .createdAt(match.getCreatedAt())
                .build();

        return ResponseEntity.ok().body(matchResDto);
    }

}
