package minskim2.JHP_World.domain.member.entity;

import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private RoleName roleName;

    @Builder
    public Role(Long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
