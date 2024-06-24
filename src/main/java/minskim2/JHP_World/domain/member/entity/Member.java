package minskim2.JHP_World.domain.member.entity;

import minskim2.JHP_World.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth2id", nullable = false, unique = true, length = 50)
    private String oauth2id;

    @Column(name = "name", length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Builder
    public Member(Long id, String oauth2id, String name, Role role) {
        this.id = id;
        this.oauth2id = oauth2id;
        this.name = name;
        this.role = role;
    }
}
