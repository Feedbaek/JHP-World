package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import minskim2.JHP_World.domain.comment.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static minskim2.JHP_World.domain.comment.dto.CommentReq.*;
import static minskim2.JHP_World.domain.comment.dto.CommentRes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/create")
    public CreateRes createComment(@AuthenticationPrincipal KakaoUser member, @RequestBody CreateReq req) {

        return commentService.createComment(member.getMemberId(), req);
    }
}
