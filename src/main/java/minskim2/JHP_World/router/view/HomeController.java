package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.global.utils.ModelSetter;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static minskim2.JHP_World.global.enums.SizeEnum.DEFAULT_PREVIEW;
import static minskim2.JHP_World.global.enums.SizeEnum.POST_LIST_PREVIEW;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class HomeController {

    private final AssignmentService assignmentService;
    private final LectureService lectureService;
    private final PostService postService;

    /**
     * 임시 홈 화면
     * TODO: 추후 리팩토링 필요
     * */
    @GetMapping("/home")
    public String home(Model model) {
        ModelSetter.setTitle(model, "Home");

        // 과제 목록 조회
        // 1. 문제해결기법 과제
        Lecture problemSolving = lectureService.findByName("문제해결기법");
        var problemSolvingPage = assignmentService.getDtoListByLectureId(problemSolving.getId(), 1, DEFAULT_PREVIEW.getSize());
        var problemSolvingList = problemSolvingPage.map(AssignmentDto::from).toList();
        model.addAttribute("problemSolvingList", problemSolvingList);

        // 2. 자료구조 과제
        Lecture dataStructure = lectureService.findByName("자료구조");
        var dataStructurePage = assignmentService.getDtoListByLectureId(dataStructure.getId(), 1, DEFAULT_PREVIEW.getSize());
        var dataStructureList = dataStructurePage.map(AssignmentDto::from).toList();
        model.addAttribute("dataStructureList", dataStructureList);

        // 3. 알고리즘설계 과제
        Lecture algorithmDesign = lectureService.findByName("알고리즘설계");
        var algorithmDesignPage = assignmentService.getDtoListByLectureId(algorithmDesign.getId(), 1, DEFAULT_PREVIEW.getSize());
        var algorithmDesignList = algorithmDesignPage.map(AssignmentDto::from).toList();
        model.addAttribute("algorithmDesignList", algorithmDesignList);

        // 4. 오토마타 과제
        Lecture automata = lectureService.findByName("오토마타");
        var automataPage = assignmentService.getDtoListByLectureId(automata.getId(), 1, DEFAULT_PREVIEW.getSize());
        var automataList = automataPage.map(AssignmentDto::from).toList();
        model.addAttribute("automataList", automataList);

        // 토론방 조회
        var postList = postService.findAllByLectureId(null, 1, POST_LIST_PREVIEW.getSize());
        model.addAttribute("postList", postList);

        return "/pages/home";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {

        ModelSetter.setTitle(model, "My Page");
        return "/pages/myPage";
    }
}
