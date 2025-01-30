package minskim2.JHP_World.domain.grade.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.solution.entity.Solution;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.global.entity.BaseEntity;

import java.util.Map;

@Getter
@Entity
@Table(name = "grade_history")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Grade extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "assignment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Assignment assignment;

    @JoinColumn(name = "solution_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Solution solution;

    @JoinColumn(name = "test_case_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TestCase testCase;

    @Column(name = "success", columnDefinition = "boolean")
    private Boolean success;

    @Column(name = "result", columnDefinition = "text")
    private String result;


    public static Grade ById(Long id) {
        return Grade.builder()
                .id(id)
                .build();
    }

    public void update(Boolean success, String result) {
        this.success = success;
        this.result = result;
    }
}
