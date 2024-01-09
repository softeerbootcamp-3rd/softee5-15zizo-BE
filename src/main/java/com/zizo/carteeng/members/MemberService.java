package com.zizo.carteeng.members;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.matches.MatchService;
import com.zizo.carteeng.members.dto.MemberStatusAction;
import com.zizo.carteeng.members.dto.PostSignUpReqDto;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MatchService matchService;
    private final MemberRepository memberRepository;

    public Member createMember(PostSignUpReqDto body) {
        Member member = Member.builder()
                .nickname(body.getNickname())
                .gender(body.getGender())
                .age(body.getAge())
                .info(body.getInfo())
                .hasCompany(body.getHasCompany())
                .companyInfo(body.getCompanyInfo())
                .hasCar(body.getHasCar())
                .location(body.getLocation().toPoint())
                .status(MemberStatus.AVAILABLE)
                .build();
        return memberRepository.save(member);
    }

    public Member deleteMember(Long memberId) {

        Member member = findById(memberId);

        member.updateMemberStatus(MemberStatus.DELETED);

        return member;
    }

    public List<Member> getAllAvailableMembersByHasCar(Boolean hasCar) {
        return memberRepository.findByHasCarAndStatus(hasCar, MemberStatus.AVAILABLE);
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ErrorException(ErrorCode.MEMBER_NOT_FOUND));
        return member;
    }

    public Member updateStatusByAction(MemberStatusAction action, Long memberId, Long partnerId) {

        if(memberId == partnerId)
            throw new ErrorException(ErrorCode.CANNOT_MATCH_BY_MYSELF);

        Member member = findById(memberId);
        Member partner = findById(partnerId);

        action.apply(member, partner);

        if(action == MemberStatusAction.MEET)
            matchService.createMatch(member, partner);

        return member;
    }
}
