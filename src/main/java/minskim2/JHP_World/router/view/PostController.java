package minskim2.JHP_World.router.view;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.comment.service.CommentService;
import minskim2.JHP_World.domain.common.ModelSetter;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static minskim2.JHP_World.global.enums.SizeEnum.POST_LIST_DEFAULT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final LectureService lectureService;

    /**
     * Path Parameter 로 받은 id에 해당하는 게시글을 조회하는 메서드
     */
    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {

        // id에 해당하는 게시글을 조회하는 서비스 호출
        var post = postService.findById(id);
        var commentList = commentService.getCommentList(id);

        // 조회한 게시글을 Model에 담아서 post.html로 전달
        ModelSetter.init(model, post.title(), null, "/post/" + id);
        model.addAttribute("post", post);
        model.addAttribute("commentList", commentList);

        return "/pages/post";
    }

    /**
     * lectureId에 해당하는 게시글 목록을 조회하는 메서드
     */
    @GetMapping("/list")
    public String getPostListByLectureId(@RequestParam(required = false) Long lectureId,
            @Positive @RequestParam(defaultValue = "1", required = false) int page,
            Model model) {

        // lectureId에 해당하는 게시글 목록을 조회하는 서비스 호출
        var postList = postService.findAllByLectureId(lectureId, page, POST_LIST_DEFAULT.getSize());

        // 조회한 게시글 목록을 Model에 담아서 postList.html로 전달
        if (lectureId != null) {
            ModelSetter.init(model, "Post List", page, "/post/list?lectureId=" + lectureId);
        } else {
            ModelSetter.init(model, "Post List", page, "/post/list");
        }
        model.addAttribute("postList", postList);

        var lectureList = lectureService.findAll();
        model.addAttribute("lectureList", lectureList);

        return "/pages/postList";
    }
}
