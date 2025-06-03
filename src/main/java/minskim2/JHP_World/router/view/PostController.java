package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.domain.comment.dto.CommentRes;
import minskim2.JHP_World.domain.comment.service.CommentService;
import minskim2.JHP_World.domain.lecture.dto.LectureDto;
import minskim2.JHP_World.global.utils.ModelSetter;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.dto.PostRes;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.data.domain.Page;
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
        PostRes.GetPreviewRes post = postService.findById(id);
        List<CommentRes.GetRes> commentList = commentService.getCommentList(id);

        // 조회한 게시글을 Model에 담아서 post.html로 전달
        ModelSetter.init(model, post.title(), null, null, "/post/" + id);
        model.addAttribute("post", post);
        model.addAttribute("commentList", commentList);

        return "pages/post";
    }

    /**
     * lectureId에 해당하는 게시글 목록을 조회하는 메서드
     */
    @GetMapping("/list")
    public String getPostListByLectureId(@RequestParam(required = false) Long lectureId, @PageParam int page, Model model) {

        // lectureId에 해당하는 게시글 목록을 조회하는 서비스 호출
        Page<PostRes.GetPreviewRes> postList = postService.findAllByLectureId(lectureId, page, POST_LIST_DEFAULT.getSize());
        int totalPages = postList.getTotalPages();

        // 조회한 게시글 목록을 Model에 담아서 postList.html로 전달
        if (lectureId != null) {
            ModelSetter.init(model, "토론방", page, totalPages, "/post/list?lectureId=" + lectureId);
        } else {
            ModelSetter.init(model, "토론방", page, totalPages, "/post/list");
        }
        model.addAttribute("postList", postList);

        List<LectureDto> lectureList = lectureService.findAll();
        model.addAttribute("lectureList", lectureList);

        return "pages/postList";
    }
}
