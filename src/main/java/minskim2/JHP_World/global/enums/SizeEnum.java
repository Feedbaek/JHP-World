package minskim2.JHP_World.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SizeEnum {

    ASSIGNMENT_LIST(9),
    POST_LIST_DEFAULT(9),

    DEFAULT_PREVIEW(3),
    POST_LIST_PREVIEW(5),

    ;

    private final int size;
}
