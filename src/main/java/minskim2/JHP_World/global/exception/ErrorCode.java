package minskim2.JHP_World.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 10000 ~ 10999: Login
    LOGIN_FAILED(104011, "로그인 실패", HttpStatus.UNAUTHORIZED),
    MEMBER_DISABLED(104012, "비활성화된 계정입니다", HttpStatus.UNAUTHORIZED),

    // 11000 ~ 11999: Member
    MEMBER_NOT_FOUND(11404, "멤버를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    // 12000 ~ 12999: Role
    ROLE_NOT_FOUND(12404, "역할을 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_NOT_ADMIN(12405,"admin 권한이 아닙니다.", HttpStatus.FORBIDDEN),
    // 13000 ~ 13999: Assignment
    ASSIGNMENT_FILE_UPLOAD_FAILED(13401, "과제 파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // 14000 ~ 14999: TestCase
    TEST_CASE_NOT_FOUND(14404, "테스트 케이스를 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    // 90000 ~ 99999: Common
    PAGE_NOT_POSITIVE(90400, "페이지는 1 이상이어야 합니다", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
