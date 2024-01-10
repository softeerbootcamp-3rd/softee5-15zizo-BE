package com.zizo.carteeng.members;

import com.zizo.carteeng.members.dto.*;
import com.zizo.carteeng.members.model.Member;
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

    private final Integer SESSION_TTL = 60;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResDto> postSignUp(@RequestBody @Valid PostSignUpReqDto body, HttpServletRequest request) {

        Member member = memberService.createMember(body);

        HttpSession session = request.getSession();
        session.setAttribute(Member.KEY_COLUMN, member.getId());

        session.setMaxInactiveInterval(SESSION_TTL);

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<MemberResDto> postLogout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute(Member.KEY_COLUMN);
        Member member = memberService.deleteMember(memberId);

        session.invalidate();

        MemberResDto response = MemberResDto.of(member);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MemberResDto>> getMembers(@SessionAttribute(Member.KEY_COLUMN) Long memberId) {

        Boolean hasCar = memberService.findById(memberId).getHasCar();

        List<MemberResDto> response = memberService.getAllAvailableMembersByHasCar(!hasCar).stream()
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

    @GetMapping("/me")
    public ResponseEntity<MeResDto> getMe(@SessionAttribute(Member.KEY_COLUMN) Long memberId) {

        Member member = memberService.findById(memberId);

        MeResDto response = MeResDto.of(member);

        return ResponseEntity.ok(response);
    }
}