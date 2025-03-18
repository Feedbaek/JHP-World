package minskim2.JHP_World.domain.visitor_log.entity;

import jakarta.persistence.*;
import lombok.*;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "visitor_log")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitorLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "session_id", nullable = false, length = 50)
    private String sessionId;

    @Column(name = "old_session_id", length = 50)
    private String oldSessionId;

    @Column(name = "ip", nullable = false, length = 30)
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "user_name", length = 30)
    private String userName;
}
