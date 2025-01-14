package minskim2.JHP_World.domain.lecture.dto;

import lombok.Builder;
import lombok.Getter;
import minskim2.JHP_World.domain.lecture.entity.Lecture;

@Getter
public class LectureDto {
    private final Long id;
    private final String name;

    @Builder
    public LectureDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LectureDto from(Lecture lecture) {
        return LectureDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .build();
    }
}
