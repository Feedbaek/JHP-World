package minskim2.JHP_World.domain.grade.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.solution.entity.Solution;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.global.entity.BaseEntity;

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

    @JoinColumn(name = "solution_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Solution solution;

    @JoinColumn(name = "test_case_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TestCase testCase;

    @Column(name = "message", nullable = false, columnDefinition = "varchar(30)")
    private String message;

    @Column(name = "result", nullable = false, columnDefinition = "text")
    private String result;
}
