package com.zizo.carteeng.members;

import com.zizo.carteeng.members.dto.PostSignUpResDto;
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

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    ResponseEntity<Member> test(HttpServletRequest request) {
        System.out.println(request.getSession().getAttribute("member_id"));

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/api/v1/members")
    ResponseEntity<PostSignUpResDto> postSignUp(@RequestBody @Valid PostSignUpReqDto body, HttpServletRequest request) {

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

        PostSignUpResDto response = PostSignUpResDto.of(member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
