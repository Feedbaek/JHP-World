package minskim2.JHP_World.domain.assignment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.entity.Assignment;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignmentDto {

    private final Long id;
    private final String title;
    private final String body;
    private final Long lectureId;

    public static AssignmentDto from(AssignmentQ query) {
        return AssignmentDto.builder()
                .id(query.getId())
                .title(query.getTitle())
                .body(query.getBody())
                .lectureId(query.getLectureId())
                .build();
    }

    public static AssignmentDto from(Assignment entity) {
        return AssignmentDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .lectureId(entity.getLecture().getId())
                .build();
    }
}
