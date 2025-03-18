package minskim2.JHP_World.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.global.entity.BaseEntity;

@Entity
@Table(name = "role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.id = roleName.getId();
        this.roleName = roleName;
    }
}
