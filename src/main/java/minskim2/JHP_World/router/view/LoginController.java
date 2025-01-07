package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    // 로그인 페이지로 이동
    @GetMapping
    public String login() {
        return "/pages/login";
    }

    // 카카오 로그인 페이지로 리다이렉트
    @GetMapping("/kakao")
    public String loginKakao() {
        return "redirect:/oauth2/authorization/kakao";
    }
}
