package minskim2.JHP_World.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.notification.entity.Notification;
import minskim2.JHP_World.domain.notification.repository.NotificationRepository;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.domain.post.repository.PostRepository;
import minskim2.JHP_World.domain.visitor_log.repository.VisitorLogRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static minskim2.JHP_World.domain.notification.dto.NotificationRes.*;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "notification")
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final VisitorLogRepository visitorLogRepository;
    private final RedisIndexedSessionRepository sessionRepository;

    /**
     * 댓글 작성 알림
     * 댓글 작성 시 sender가 게시글 작성자에게 알림
     * */
    @Transactional
    public void notifyPostOwner(Long senderId, Long postId) {
        // 게시글 작성자 확인
        Member sender = memberRepository.findById(senderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 회원이 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        );
        Member receiver = post.getMember();

        // 댓글 작성자가 본인이라면 알림 생략
        if (receiver.getId().equals(senderId)) {
            return;
        }

        // 알림 저장
        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .post(post)
                .message(sender.getName() + "님이 댓글을 남겼습니다.")
                .isRead(false)
                .build();

        notificationRepository.save(notification);

        // 세션 id 조회
        Optional<String> sessionId = visitorLogRepository.findSessionIdByMemberId(receiver.getId());
        // 읽지 않은 알림 여부 저장/갱신
        sessionId.ifPresent(this::checkNotification);
    }

    // 세션에 읽지 않은 알림 여부 저장
    public void checkNotification(String sessionId) {
        // 특정 멤버의 세션 가져오기
        var redisSession = sessionRepository.findById(sessionId);

        if (redisSession != null) {
            // 세션에 읽지 않은 알림 여부 저장
            ((Session) redisSession).setAttribute("notification", true);
            sessionRepository.save(redisSession);
        }
    }

    /**
     * 특정 알림 읽음 처리
     * */
    @Transactional
    public void markAsRead(Long notificationId, Long userId) {

        // 특정 알림 읽음 처리
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
        // 알림을 읽을 권한이 있는지 확인
        if (!notification.getReceiver().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 알림을 읽음 처리 할 권한이 없습니다.");
        }

        // 읽음 처리
        Notification.markAsRead(notification);

        notificationRepository.save(notification);
    }

    // DB에서 읽지 않은 알림 여부 확인
    public boolean hasUnreadNotifications(Long userId) {
        return notificationRepository.existsByReceiverIdAndIsReadFalse(userId);
    }

    public List<GetRes> getNotifications(Long userId) {
        return notificationRepository.findUnreadNotifications(userId).stream()
                .map(GetRes::from)
                .toList();
    }
}
