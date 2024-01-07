package com.zizo.carteeng.members;

import com.zizo.carteeng.members.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
