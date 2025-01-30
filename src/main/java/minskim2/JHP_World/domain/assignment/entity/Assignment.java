package minskim2.JHP_World.domain.assignment.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.dto.AssignmentReq;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "assignment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Assignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: 과제 제목 필드 추가
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    public static Assignment ById(Long assignmentId) {
        return Assignment.builder()
                .id(assignmentId)
                .build();
    }


    public void update(AssignmentReq.Update assignmentDto) {
        this.title = assignmentDto.title();
        this.body = assignmentDto.body();
    }
}
