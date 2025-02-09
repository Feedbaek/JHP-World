package minskim2.JHP_World.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleName {
    ADMIN(1L, "ROLE_ADMIN"),
    USER(2L, "ROLE_USER") ,
    ;

    private final Long id;
    private final String value;
}
