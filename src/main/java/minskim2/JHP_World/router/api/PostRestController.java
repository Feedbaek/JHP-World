package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static minskim2.JHP_World.domain.post.dto.PostReq.*;
import static minskim2.JHP_World.domain.post.dto.PostRes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostRestController {

    private final PostService postService;

    /**
     * 토론글 작성
     * */
    @PostMapping("/create")
    public CreateRes createPost(@AuthenticationPrincipal CustomOAuth2User member, @Validated @RequestBody CreateReq req) {

        return postService.createPost(member.getMemberId(), req);
    }
}
