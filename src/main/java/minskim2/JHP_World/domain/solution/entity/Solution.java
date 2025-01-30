package minskim2.JHP_World.domain.solution.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "solution")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Solution extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_code", nullable = false, columnDefinition = "TEXT")
    private String sourceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Builder
    public Solution(Long id, String sourceCode, Member member, Assignment assignment) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.member = member;
        this.assignment = assignment;
    }

    public static Solution ById(Long id) {
        return Solution.builder()
                .id(id)
                .build();
    }
}
