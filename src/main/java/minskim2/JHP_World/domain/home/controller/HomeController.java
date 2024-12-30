package minskim2.JHP_World.domain.home.controller;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.common.ModelSetter;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static minskim2.JHP_World.global.constant.IntConstant.PREVIEW_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final AssignmentService assignmentService;
    private final LectureService lectureService;

    /**
     * 임시 홈 화면
     * */
    @GetMapping
    public String home(Model model) {
        ModelSetter.setTitle(model,null, "Home");

        // 과제 목록 조회
        // 1. 문제해결기법 과제
        Lecture problemSolving = lectureService.findByName("문제해결기법");
        List<AssignmentDto> problemSolvingList = assignmentService.getDtoListByLectureId(problemSolving.getId(), 0, PREVIEW_SIZE);
        model.addAttribute("problemSolvingList", problemSolvingList);
        // 2. 자료구조 과제
        Lecture dataStructure = lectureService.findByName("자료구조");
        List<AssignmentDto> dataStructureList = assignmentService.getDtoListByLectureId(dataStructure.getId(), 0, PREVIEW_SIZE);
        model.addAttribute("dataStructureList", dataStructureList);
        // 3. 알고리즘설계 과제
        Lecture algorithmDesign = lectureService.findByName("알고리즘설계");
        List<AssignmentDto> algorithmDesignList = assignmentService.getDtoListByLectureId(algorithmDesign.getId(), 0, PREVIEW_SIZE);
        model.addAttribute("algorithmDesignList", algorithmDesignList);
        // 4. 오토마타 과제
        Lecture automata = lectureService.findByName("오토마타");
        List<AssignmentDto> automataList = assignmentService.getDtoListByLectureId(automata.getId(), 0, PREVIEW_SIZE);
        model.addAttribute("automataList", automataList);

        return "/pages/home";
    }
}
