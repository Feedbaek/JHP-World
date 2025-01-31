package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    /**
     * 관리자 페이지
     * */
    @GetMapping("/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHomePage() {
        return "/pages/admin/home";
    }

    /**
     * 관리자 로그인 페이지
     * */
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "/pages/admin/login";
    }

    /**
     * 관리자의 사용자 관리 페이지
     * */
    @GetMapping("/member")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditPage() {
        return "/pages/admin/member";
    }

    /**
     * 관리자의 과제 관리 페이지
     * */
    @GetMapping("/assignment")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAssignmentPage() {
        return "/pages/admin/assignment";
    }

    /**
     * 관리자의 테스트 케이스 관리 페이지
     * */
    @GetMapping("/testcase")
    @PreAuthorize("hasRole('ADMIN')")
    public String getTestCasePage() {
        return "/pages/admin/testcase";
    }

    /**
     * 관리자의 토론글 관리 페이지
     * */
    @GetMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    public String getPostPage() {
        return "/pages/admin/post";
    }
}
