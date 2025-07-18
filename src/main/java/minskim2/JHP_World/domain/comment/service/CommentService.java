package minskim2.JHP_World.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.comment.entity.Comment;
import minskim2.JHP_World.domain.comment.repository.CommentRepository;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static minskim2.JHP_World.domain.comment.dto.CommentReq.*;
import static minskim2.JHP_World.domain.comment.dto.CommentRes.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j(topic = "CommentService")
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    /**
     * 댓글 생성 및 알림 생성
     * */
    @Transactional
    public CreateRes createComment(Long senderId, CreateReq req) {

        Member member = Member.ById(senderId);
        Post post = postRepository.findPostAndMemberById(req.postId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없습니다."));

        Comment comment = Comment.builder()
                .member(member)
                .board(post)
                .body(req.body())
                .build();

        commentRepository.save(comment);

        // 알림 생성
        notificationService.notifyPostOwner(senderId, req.postId());

        return CreateRes.from(comment);
    }

    /**
     * 댓글 목록 조회
     * */
    public List<GetRes> getCommentList(Long postId) {
        List<Comment> comments = commentRepository.findAllByBoard_Id(postId);

        return comments.stream()
                .map(GetRes::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long deleteComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당하는 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);

        return commentId;
    }
}
