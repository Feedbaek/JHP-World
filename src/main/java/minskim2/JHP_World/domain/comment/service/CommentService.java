package minskim2.JHP_World.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.comment.entity.Comment;
import minskim2.JHP_World.domain.comment.repository.CommentRepository;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.post.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.domain.comment.dto.CommentReq.*;
import static minskim2.JHP_World.domain.comment.dto.CommentRes.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j(topic = "CommentService")
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CreateRes createComment(Long memberId, CreateReq req) {

        Member member = Member.builder()
                .id(memberId)
                .build();

        Post post = Post.builder()
                .id(req.postId())
                .build();

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .body(req.body())
                .build();

        commentRepository.save(comment);

        return CreateRes.from(comment);
    }
}
