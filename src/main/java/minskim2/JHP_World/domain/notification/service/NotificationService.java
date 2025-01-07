package minskim2.JHP_World.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.notification.entity.Notification;
import minskim2.JHP_World.domain.notification.repository.NotificationRepository;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.domain.post.repository.PostRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static minskim2.JHP_World.domain.notification.dto.NotificationRes.*;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "notification")
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


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

        // 읽지 않은 알림 여부 저장/갱신 (Redis)
        checkNotification(receiver.getId());
    }

    // Redis에 읽지 않은 알림 여부 저장
    public void checkNotification(Long memberId) {
        redisTemplate.opsForValue().set("notifications:unread:" + memberId, true);
    }

    /**
     * 특정 알림 읽음 처리
     * 알림 하나만 읽어도 모두 알림 표시는 삭제
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

    // Redis에서 읽지 않은 상태 제거
    public void deleteRedisMarkAsRead(Long userId) {
        String redisKey = "notifications:unread:" + userId;
        redisTemplate.delete(redisKey);
    }

    public boolean hasUnreadNotifications(Long userId) {
        return redisTemplate.hasKey("notifications:unread:" + userId);
    }

    public List<GetRes> getNotifications(Long userId) {

        List<Notification> notifications = notificationRepository.findUnreadNotifications(userId);

        return notifications.stream()
                .map(GetRes::from)
                .toList();
    }
}
