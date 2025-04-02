package minskim2.JHP_World.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "post")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;


    public static Post ById(Long id) {
        return Post.builder()
                .id(id)
                .build();
    }
}
