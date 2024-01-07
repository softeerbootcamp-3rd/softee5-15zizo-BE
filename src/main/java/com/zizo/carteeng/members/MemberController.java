package com.zizo.carteeng.members;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
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

    @GetMapping("/api/v1/members")
    ResponseEntity<List<MemberResDto>> getMembers() {
        List<MemberResDto> response =  memberService.getAllMembers().stream()
                .map(member -> MemberResDto.of(member))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
