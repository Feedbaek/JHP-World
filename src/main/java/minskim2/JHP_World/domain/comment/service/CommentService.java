package minskim2.JHP_World.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.comment.dto.CommentRes;
import minskim2.JHP_World.domain.comment.entity.Comment;
import minskim2.JHP_World.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CommentService")
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentRes.create createComment() {

        Comment comment = Comment.builder()

                .body("test")
                .build();

        return null;
    }
}
