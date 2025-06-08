package minskim2.JHP_World.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.board.entity.Board;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.member.entity.Member;

@Getter
@Entity
@Table(name = "post")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Board {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;


    @Builder
    public Post(Long id, Member member, Lecture lecture, String title, String body) {
        super(id, member);
        this.lecture = lecture;
        this.title = title;
        this.body = body;
    }

    public static Post ById(Long id) {
        return Post.builder()
                .id(id)
                .build();
    }
}
