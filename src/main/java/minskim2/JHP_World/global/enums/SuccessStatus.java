package minskim2.JHP_World.global.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus {
    GRADE_SUCCESS(200, "테스트 실행 성공"),

    ;

    private final int status;
    private final String message;
}
