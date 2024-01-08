package com.zizo.carteeng.common.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    MATCH_MYSELF(HttpStatus.BAD_REQUEST, "자기 자신과는 매치될 수 없습니다."),
    NOT_PARTNER(HttpStatus.BAD_REQUEST, "잘못된 상대방입니다."),
    CANNOT_MATCH(HttpStatus.BAD_REQUEST, "매치될 수 없는 상대입니다."),
    MEMBER_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "매치 가능한 상태가 아닙니다."),
    MEMBER_NOT_ACCEPT(HttpStatus.BAD_REQUEST, "수락 가능 상태가 아닙니다."),
    MEMBER_NOT_REJECT(HttpStatus.BAD_REQUEST, "거절 가능 상태가 아닙니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
//    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
//    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
//    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
