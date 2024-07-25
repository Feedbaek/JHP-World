package minskim2.JHP_World.domain.test_case.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.subject.entity.Subject;
import minskim2.JHP_World.global.entity.BaseEntity;

@Entity
@Table(name = "test_case")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestCase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

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
    public TestCase(Long id, Subject subject, Member member, String input, String output, String description) {
        this.id = id;
        this.subject = subject;
        this.member = member;
        this.input = input;
        this.output = output;
        this.description = description;
    }
}
