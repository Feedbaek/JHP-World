package minskim2.JHP_World.domain.file.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "ext", length = 15)
    private String ext;


    public static File ById(Long fileId) {
        return File.builder()
                .id(fileId)
                .build();
    }
}
