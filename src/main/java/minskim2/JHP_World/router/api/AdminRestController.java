package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentReq;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AssignmentService assignmentService;

    /**
     * 과제 생성
     * */
    @PostMapping("/assignment")
    public Long createAssignment(@Validated @RequestBody AssignmentReq.Create req) {
        return assignmentService.createAssignment(req);
    }

    /**
     * 과제 수정
     * */
    @PutMapping("/assignment")
    public Long updateAssignment(@Validated @RequestBody AssignmentReq.Update req) {
        return assignmentService.updateAssignment(req);
    }

    /**
     * 과제 삭제
     * */
    @DeleteMapping("/assignment")
    public Long deleteAssignment(@Validated @RequestBody AssignmentReq.Delete req) {
        return assignmentService.deleteAssignment(req);
    }
}
