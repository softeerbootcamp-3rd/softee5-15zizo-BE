package com.zizo.carteeng.members.dto;

import lombok.Getter;

@Getter
public class ActionReqDto {
    private MemberStatusAction action;
    private Long partnerId;
}
