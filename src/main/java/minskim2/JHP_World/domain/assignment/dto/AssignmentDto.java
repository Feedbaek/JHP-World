package minskim2.JHP_World.domain.assignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AssignmentDto {
    private final Long id;
    private final String body;
    private final Long lectureId;

    @Builder
    public AssignmentDto(Long id, String body, Long lectureId) {
        this.id = id;
        this.body = body;
        this.lectureId = lectureId;
    }
}
