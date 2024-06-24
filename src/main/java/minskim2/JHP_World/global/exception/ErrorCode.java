package minskim2.JHP_World.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    // Basic HTTP Status Code
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 10000 ~ 19999: Login, JWT
    LOGIN_FAILED(10401, "로그인 실패"),

    JWT_INVALID(11400, "JWT 토큰이 유효하지 않습니다"),
    JWT_EXPIRED(11400, "JWT 토큰이 만료 되었습니다."),

    // 20000 ~ 29999: Member
    MEMBER_NOT_FOUND(20404, "멤버를 찾을 수 없습니다"),

    ;

    private final int code;
    private final String message;
}
