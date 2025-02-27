package minskim2.JHP_World.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.member.entity.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRes {

    @Builder
    public record AdminGet(
        Long id,
        String oauth2id,
        String name,
        String role,
        Boolean isEnabled
    ) {
        public static AdminGet from(Member member) {
            return new AdminGet(
                member.getId(),
                member.getOauth2id(),
                member.getName(),
                member.getRole().getRoleName().name(),
                member.isEnabled()
            );
        }
    }
}
