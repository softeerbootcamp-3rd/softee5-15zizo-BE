package com.zizo.carteeng.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class MemoryMember extends Member {

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
}
