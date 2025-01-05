package minskim2.JHP_World.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.comment.dto.CommentRes;
import minskim2.JHP_World.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/create")
    public Long createComment() {

        CommentRes.create res = commentService.createComment();
        return res.id();
    }
}
