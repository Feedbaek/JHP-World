package minskim2.JHP_World.router.view;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public String notification(@AuthenticationPrincipal CustomOAuth2User member,
                               Model model, HttpSession session) {

        // 알림 목록 가져오기
        var notificationList = notificationService.getNotifications(member.getMemberId());
        model.addAttribute("notificationList", notificationList);

        // 알림 표시 제거
        session.removeAttribute("notification");
        notificationService.deleteRedisMarkAsRead(member.getMemberId());

        return "/pages/notifications";
    }
}
