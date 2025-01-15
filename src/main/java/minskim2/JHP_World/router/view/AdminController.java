package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    /**
     * 관리자 페이지
     * */
    @GetMapping("/home")
    public String adminHomePage() {
        return "/pages/admin/home";
    }

    /**
     * 관리자 로그인 페이지
     * */
    @GetMapping("/login")
    public String adminLoginPage() {
        return "/pages/admin/login";
    }

    /**
     * 관리자의 과제, 토론글, 댓글, 사용자 관리 페이지
     * */
    @GetMapping("/edit")
    public String getEditPage() {
        return "/pages/admin/edit";
    }
}
