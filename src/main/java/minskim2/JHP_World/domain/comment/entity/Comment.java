package minskim2.JHP_World.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.global.entity.BaseEntity;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    public Comment(Long id, Member member, Post post, String body) {
        this.id = id;
        this.member = member;
        this.post = post;
        this.body = body;
    }
}
