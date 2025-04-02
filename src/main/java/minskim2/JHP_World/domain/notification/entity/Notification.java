package minskim2.JHP_World.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Builder
@Table(name = "notification")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "message", nullable = false, length = 30)
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;


    public static void markAsRead(Notification notification) {
        notification.isRead = true;
    }
}
