package minskim2.JHP_World.domain.post.dto;

import lombok.Builder;

@Builder
public record PostDto (
    Long id,
    Long memberId,
    Long lectureId,
    String title,
    String body
) {

}
