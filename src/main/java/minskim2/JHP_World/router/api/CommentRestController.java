package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import minskim2.JHP_World.domain.comment.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static minskim2.JHP_World.domain.comment.dto.CommentReq.*;
import static minskim2.JHP_World.domain.comment.dto.CommentRes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("")
    public CreateRes createComment(@AuthenticationPrincipal CustomOAuth2User member, @Validated @RequestBody CreateReq req) {

        return commentService.createComment(member.getMemberId(), req);
    }

    @DeleteMapping("/{commentId}")
    public Long deleteComment(@AuthenticationPrincipal CustomOAuth2User member, @PathVariable Long commentId) {

        return commentService.deleteComment(member.getMemberId(), commentId);
    }
}
