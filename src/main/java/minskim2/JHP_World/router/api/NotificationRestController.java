package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.comment.service.CommentService;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static minskim2.JHP_World.domain.comment.dto.CommentReq.CreateReq;
import static minskim2.JHP_World.domain.comment.dto.CommentRes.CreateRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    @PostMapping("/read/{notificationId}")
    public Long readNotification(@AuthenticationPrincipal CustomOAuth2User member,
                                   @PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId, member.getMemberId());
        return notificationId;
    }
}
