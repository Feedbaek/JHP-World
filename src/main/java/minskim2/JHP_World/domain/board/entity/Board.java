package minskim2.JHP_World.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "board")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected Board(Long id, Member member) {
        this.id = id;
        this.member = member;
    }
}
