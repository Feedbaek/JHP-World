package minskim2.JHP_World.domain.lecture.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LectureDto {
    private final Long id;
    private final String name;

    @Builder
    public LectureDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
