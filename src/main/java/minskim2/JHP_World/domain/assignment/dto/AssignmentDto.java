package minskim2.JHP_World.domain.assignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AssignmentDto {
    private final Long id;
    private final String title;
    private final String body;
    private final Long lectureId;

    @Builder
    public AssignmentDto(Long id, String title, String body, Long lectureId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.lectureId = lectureId;
    }
}
