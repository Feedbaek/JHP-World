package minskim2.JHP_World.domain.assignment.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.assignment.dto.AssignmentReq;
import minskim2.JHP_World.domain.file.entity.File;
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

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id")
    private File file;


    public static Assignment ById(Long assignmentId) {
        return Assignment.builder()
                .id(assignmentId)
                .build();
    }


    public void update(AssignmentReq.Update assignmentDto) {
        if (assignmentDto.title() != null && !assignmentDto.title().isBlank()) {
            this.title = assignmentDto.title();
        }
        if (assignmentDto.body() != null && !assignmentDto.body().isBlank()) {
            this.body = assignmentDto.body();
        }
    }

    public void updateFile(File file) {
        this.file = file;
    }
}
