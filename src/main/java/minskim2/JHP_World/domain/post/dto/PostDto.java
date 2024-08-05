package minskim2.JHP_World.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {
    private final Long id;
    private final Long memberId;
    private final Long lectureId;
    private final String title;
    private final String body;

    @Builder
    public PostDto(Long id, Long memberId, Long lectureId, String title, String body) {
        this.id = id;
        this.memberId = memberId;
        this.lectureId = lectureId;
        this.title = title;
        this.body = body;
    }
}
