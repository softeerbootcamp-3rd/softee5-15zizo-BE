package com.zizo.carteeng.common.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_THE_PARTNER(HttpStatus.BAD_REQUEST, "매치된 사용자가 아닙니다."),
    NOT_MEET(HttpStatus.BAD_REQUEST, "만난 상태가 아닙니다."),
    ALREADY_MATCHED(HttpStatus.BAD_REQUEST, "이미 매치된 사용자 입니다."),
    CANNOT_MATCH_BY_MYSELF(HttpStatus.BAD_REQUEST, "자기 자신과는 매치될 수 없습니다."),
    CANNOT_MATCH(HttpStatus.BAD_REQUEST, "매치될 수 없는 상대입니다."),
    CANNOT_ACCEPT(HttpStatus.BAD_REQUEST, "수락 가능한 상태가 아닙니다."),
    CANNOT_REJECT(HttpStatus.BAD_REQUEST, "거절 가능한 상태가 아닙니다."),
    CANNOT_MEET(HttpStatus.BAD_REQUEST, "만남 가능한 상태가 아닙니다."),
    CANNOT_RESTART(HttpStatus.BAD_REQUEST, "재시작 가능한 상태가 아닙니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    PARTNER_NOT_FOUND(HttpStatus.BAD_REQUEST, "매치된 상대방이 존재하지 않습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
