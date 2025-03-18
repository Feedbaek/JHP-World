package minskim2.JHP_World.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "lecture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Builder
    public Lecture(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Lecture ById(Long id) {
        return Lecture.builder()
                .id(id)
                .build();
    }
}
