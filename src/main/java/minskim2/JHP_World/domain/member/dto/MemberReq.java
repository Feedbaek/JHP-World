package minskim2.JHP_World.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.member.entity.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberReq {

    @Builder
    public record AdminUpdate(
            Long id,
            String name,
            String role,
            Boolean isEnabled
    ) {
        public static AdminUpdate from(Member member) {
            return new AdminUpdate(
                    member.getId(),
                    member.getName(),
                    member.getRole().getRoleName().getValue(),
                    member.isEnabled()
            );
        }
    }
}
