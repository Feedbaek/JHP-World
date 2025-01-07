package minskim2.JHP_World.domain.member.entity;

import lombok.*;
import minskim2.JHP_World.global.entity.BaseEntity;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "member")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;


    public static Member ById(Long id) {
        return Member.builder()
                .id(id)
                .build();
    }
}
