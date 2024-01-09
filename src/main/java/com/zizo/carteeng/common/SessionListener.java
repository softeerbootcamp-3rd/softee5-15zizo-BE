package com.zizo.carteeng.common;

import com.zizo.carteeng.members.MemberService;
import com.zizo.carteeng.members.model.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {

    private final MemberService memberService;

    @Autowired
    public SessionListener(MemberService memberService) {
        this.memberService = memberService;
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Long memberId = (Long)session.getAttribute(Member.KEY_COLUMN);

        memberService.deleteMember(memberId);
    }
}