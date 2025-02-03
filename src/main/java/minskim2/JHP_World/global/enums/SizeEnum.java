package minskim2.JHP_World.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SizeEnum {

    // 리스트 페이징 사이즈
    ASSIGNMENT_LIST(9),
    POST_LIST_DEFAULT(9),
    TEST_CASE_LIST(10),
    GRADE_LIST(10),
    MEMBER_LIST(10),
    NOTIFICATION_LIST(10),

    // 미리보기 사이즈
    DEFAULT_PREVIEW(3),
    POST_LIST_PREVIEW(5),

    ;

    private final int size;
}
