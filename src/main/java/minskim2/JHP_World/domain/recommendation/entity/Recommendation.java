package minskim2.JHP_World.domain.recommendation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "recommendation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Builder
    public Recommendation(Long id, Member member, TestCase testCase) {
        this.id = id;
        this.member = member;
        this.testCase = testCase;
    }
}
