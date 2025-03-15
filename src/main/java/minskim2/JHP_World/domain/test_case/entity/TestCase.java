package minskim2.JHP_World.domain.test_case.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Builder
@Table(name = "test_case")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    public static TestCase ById(Long id) {
        return TestCase.builder()
                .id(id)
                .build();
    }

    public void update(TestCaseReq.Update req) {
        if (req.input() != null && !req.input().isBlank()) {
            this.input = req.input();
        }
        if (req.output() != null && !req.output().isBlank()) {
            this.output = req.output();
        }
        if (req.description() != null && !req.description().isBlank()) {
            this.description = req.description();
        }
    }

    public void updateByAdmin(TestCaseReq.UpdateByAdmin req) {
        if (req.input() != null && !req.input().isBlank()) {
            this.input = req.input();
        }
        if (req.output() != null && !req.output().isBlank()) {
            this.output = req.output();
        }
        if (req.description() != null && !req.description().isBlank()) {
            this.description = req.description();
        }
        if (req.isPublic() != null) {
            this.isPublic = req.isPublic();
        }
    }
}
