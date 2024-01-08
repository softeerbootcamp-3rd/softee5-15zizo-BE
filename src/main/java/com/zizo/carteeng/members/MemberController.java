package com.zizo.carteeng.members;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
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
        session.setAttribute("member_id", member.getId());

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResDto>> getMembers(HttpServletRequest request) {

        Long member_id = (Long)request.getSession().getAttribute("member_id");
        Boolean hasCar = memberService.findById(member_id).getHasCar(); //요청자 차 유무 조회

        List<MemberResDto> response = memberService.getAllMembersByHasCar(hasCar).stream()
                .map(member -> MemberResDto.of(member))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/members")
    public ResponseEntity<String> getMemberRequest(@RequestBody ActionReqDto actionDto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member_id");
        Long partnerId = actionDto.getPartnerId();
        MemberStatusAction action = actionDto.getAction();

        if (memberId == partnerId)
            throw new ErrorException(ErrorCode.MATCH_MYSELF);

        memberService.updateStatusByAction(action, memberId, partnerId);

        return ResponseEntity.ok().body("success");
    }
}