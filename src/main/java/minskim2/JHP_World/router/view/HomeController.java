package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.lecture.dto.LectureDto;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.dto.PostRes;
import minskim2.JHP_World.domain.post.service.PostService;
import minskim2.JHP_World.global.utils.ModelSetter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

        List<LectureDto> lectureList = new ArrayList<>();
        ModelSetter.setTitle(model, "Home");

        // 과제 목록 조회
        // 1. 문제해결기법 과제
        LectureDto problemSolving = lectureService.findByName("문제해결기법");
        Page<AssignmentDto> problemSolvingList = assignmentService.getDtoListByLectureId(problemSolving.getId(), 0, DEFAULT_PREVIEW.getSize());
        lectureList.add(problemSolving);
        model.addAttribute("problemSolvingList", problemSolvingList);

        // 2. 자료구조 과제
        LectureDto dataStructure = lectureService.findByName("자료구조");
        Page<AssignmentDto> dataStructureList = assignmentService.getDtoListByLectureId(dataStructure.getId(), 0, DEFAULT_PREVIEW.getSize());
        lectureList.add(dataStructure);
        model.addAttribute("dataStructureList", dataStructureList);

        // 3. 알고리즘설계 과제
        LectureDto algorithmDesign = lectureService.findByName("알고리즘설계");
        Page<AssignmentDto> algorithmDesignList = assignmentService.getDtoListByLectureId(algorithmDesign.getId(), 0, DEFAULT_PREVIEW.getSize());
        lectureList.add(algorithmDesign);
        model.addAttribute("algorithmDesignList", algorithmDesignList);

        // 4. 오토마타 과제
        LectureDto automata = lectureService.findByName("오토마타");
        Page<AssignmentDto> automataList = assignmentService.getDtoListByLectureId(automata.getId(), 0, DEFAULT_PREVIEW.getSize());
        lectureList.add(automata);
        model.addAttribute("automataList", automataList);

        // 토론방 조회
        Page<PostRes.GetPreviewRes> postList = postService.findAllByLectureId(null, 0, POST_LIST_PREVIEW.getSize());
        model.addAttribute("postList", postList);

        // 강의 리스트 추가
        model.addAttribute("lectureList", lectureList);

        return "pages/home";
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {

        ModelSetter.setTitle(model, "My Page");
        return "pages/myPage";
    }
}
