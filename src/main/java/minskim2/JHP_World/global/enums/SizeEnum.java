package minskim2.JHP_World.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SizeEnum {

    ASSIGNMENT_LIST(10),
    POST_LIST_DEFAULT(10),

    ;

    private final int Size;
}
