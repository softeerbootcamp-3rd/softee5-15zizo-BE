package com.zizo.carteeng.members;

import com.zizo.carteeng.members.dto.ActionReqDto;
import com.zizo.carteeng.members.dto.MemberResDto;
import com.zizo.carteeng.members.dto.MemberStatusAction;
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

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<MemberResDto> postSignUp(@RequestBody @Valid PostSignUpReqDto body, HttpServletRequest request) {

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
        session.setAttribute("member", member);

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping()
    public ResponseEntity<MemberResDto> deleteReset(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        member = memberService.releasePartnerFromMeet(member);

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<MemberResDto>> getMembers() {
        List<MemberResDto> response = memberService.getAllMembers().stream()
                .map(member -> MemberResDto.of(member))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("")
    public ResponseEntity<String> getMemberRequest(@RequestBody ActionReqDto actionDto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        Long partnerId = actionDto.getPartnerId();
        MemberStatusAction action = actionDto.getAction();

        memberService.updateStatusByAction(action, member, partnerId);

        return ResponseEntity.ok().body("success");
    }
}