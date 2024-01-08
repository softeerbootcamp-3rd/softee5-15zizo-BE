package com.zizo.carteeng.members;

import com.zizo.carteeng.members.dto.ActionReqDto;
import com.zizo.carteeng.members.dto.MemberResDto;
import com.zizo.carteeng.members.dto.MemberStatusAction;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.dto.PostSignUpReqDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResDto> postSignUp(@RequestBody @Valid PostSignUpReqDto body, HttpServletRequest request) {

        Member member = memberService.createMember(body);

        HttpSession session = request.getSession();
        session.setAttribute(Member.KEY_COLUMN, member.getId());

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<MemberResDto> postLogout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute(Member.KEY_COLUMN);

        session.invalidate();

        Member member = memberService.findById(memberId);
        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MemberResDto>> getMembers(@SessionAttribute(Member.KEY_COLUMN) Long memberId) {

        Boolean hasCar = memberService.findById(memberId).getHasCar(); //요청자 차 유무 조회

        List<MemberResDto> response = memberService.getAllMembersByHasCar(!hasCar).stream()
                .map(member -> MemberResDto.of(member))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<MemberResDto> patchMemberStatus(@RequestBody ActionReqDto actionDto, @SessionAttribute(Member.KEY_COLUMN) Long memberId) {

        Long partnerId = actionDto.getPartnerId();
        MemberStatusAction action = actionDto.getAction();

        Member member = memberService.updateStatusByAction(action, memberId, partnerId);

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.ok(response);
    }
}