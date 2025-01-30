package minskim2.JHP_World.domain.test_case.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "test_case")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestCase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "input", nullable = false, columnDefinition = "TEXT")
    private String input;

    @Column(name = "output", nullable = false, columnDefinition = "TEXT")
    private String output;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Builder
    public TestCase(Long id, Assignment assignment, Member member, String input, String output, String description) {
        this.id = id;
        this.assignment = assignment;
        this.member = member;
        this.input = input;
        this.output = output;
        this.description = description;
    }

    public static TestCase ById(Long id) {
        return TestCase.builder()
                .id(id)
                .build();
    }
}
