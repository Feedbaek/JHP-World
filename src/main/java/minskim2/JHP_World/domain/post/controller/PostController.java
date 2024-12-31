package minskim2.JHP_World.domain.post.controller;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static minskim2.JHP_World.global.enums.SizeEnum.POST_LIST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    /**
     * Path Parameter 로 받은 id에 해당하는 게시글을 조회하는 메서드
     */
    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {

        // id에 해당하는 게시글을 조회하는 서비스 호출
        var post = postService.findById(id);

        // 조회한 게시글을 Model에 담아서 post.html로 전달
        model.addAttribute("post", post);

        return "/pages/post";
    }

    /**
     * lectureId에 해당하는 게시글 목록을 조회하는 메서드
     */
    @GetMapping("/list")
    public String getPostListByLectureId(@RequestParam Long lectureId, @RequestParam int page, Model model) {

        // lectureId에 해당하는 게시글 목록을 조회하는 서비스 호출
        var postList = postService.findAllByLectureId(lectureId, page, POST_LIST.getSize());

        // 조회한 게시글 목록을 Model에 담아서 postList.html로 전달
        model.addAttribute("postList", postList);

        return "/pages/postList";
    }
}