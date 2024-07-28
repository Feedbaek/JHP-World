package minskim2.JHP_World.domain.assignment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "assignment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Builder
    public Assignment(Long id, String body, Lecture lecture) {
        this.id = id;
        this.body = body;
        this.lecture = lecture;
    }
}
