package minskim2.JHP_World.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 10000 ~ 10999: Login
    LOGIN_FAILED(10401, "로그인 실패", HttpStatus.UNAUTHORIZED),
    // 11000 ~ 11999: Member
    MEMBER_NOT_FOUND(11404, "멤버를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    // 12000 ~ 12999: Role
    ROLE_NOT_FOUND(12404, "역할을 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
