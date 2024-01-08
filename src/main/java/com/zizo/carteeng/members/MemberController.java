package com.zizo.carteeng.members;

import com.zizo.carteeng.members.dto.ActionReqDto;
import com.zizo.carteeng.members.dto.MemberResDto;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.dto.PostSignUpReqDto;
import com.zizo.carteeng.members.model.MemberStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    ResponseEntity<MemberResDto> postSignUp(@RequestBody @Valid PostSignUpReqDto body, HttpServletRequest request) {

        Member member = memberService.createMember(
                Member.builder()
                        .nickname(body.getNickname())
                        .gender(body.getGender())
                        .info(body.getInfo())
                        .hasCompany(body.getHasCompany())
                        .companyInfo(body.getCompanyInfo())
                        .hasCar(body.getHasCar())
                        .location(body.getLocation().toPoint())
                        .status(MemberStatus.AVAILABLE)
                        .build()
        );

        HttpSession session = request.getSession();
        session.setAttribute("member_id", member.getId());

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/members")
    ResponseEntity<List<MemberResDto>> getMembers() {
        List<MemberResDto> response = memberService.getAllMembers().stream()
                .map(member -> MemberResDto.of(member))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/members")
    public ResponseEntity<String> getMemberRequest(@RequestBody ActionReqDto actionDto, HttpServletRequest request) {
        ResponseEntity<String> response;

        HttpSession session = request.getSession();
        Long member_id = (Long) session.getAttribute("member_id");
        Member partner = memberService.findById(actionDto.getPartnerId());
        Member member = memberService.findById(member_id);

        String action = actionDto.getAction();

        if (action.equals("request")) { //나 얘 맘에들어 신청
            memberService.updateStatusByAction(action, member, partner);
        } else if (action.equals("reject")) { //거절 or 취소
            memberService.updateStatusByAction(action, member, partner);
        } else if (action.equals("accept")) { //수락
            memberService.updateStatusByAction(action, member, partner);
        } else { //action에 이상한 요청
            return ResponseEntity.status(400).body("잘못된 action 요청입니다.");
        }

        response = ResponseEntity.ok().body("success");
        return response;
    }

}
