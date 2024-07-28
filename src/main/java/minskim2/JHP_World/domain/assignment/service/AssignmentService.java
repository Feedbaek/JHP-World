package minskim2.JHP_World.domain.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.assignment.repository.AssignmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static minskim2.JHP_World.global.constant.IntConstant.ASSIGNMENT_LIST_SIZE;

@Service
@Slf4j(topic = "AssignmentService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    /**
     * Assignment 엔티티를 AssignmentDto로 변환하는 메소드
     * */
    public AssignmentDto convertToDto(Assignment assignment) {
        return AssignmentDto.builder()
                .id(assignment.getId())
                .body(assignment.getBody())
                .lectureId(assignment.getLecture().getId())
                .build();
    }

    // TODO: 아래 메소드들 예외 처리 추가
    public void save(Assignment assignment) {
        try {
            assignmentRepository.save(assignment);
        } catch (Exception e) {
            log.error("과목 저장 중 오류 발생", e);
        }
    }

    public void deleteById(Long assignmentId) {
        try {
            assignmentRepository.deleteById(assignmentId);
        } catch (Exception e) {
            log.error("과목 삭제 중 오류 발생", e);
        }
    }

    public List<AssignmentDto> findAllByLectureId(Long lectureId, int page) {
        Pageable pageable = PageRequest.of(page, ASSIGNMENT_LIST_SIZE, Sort.by("createdDate"));
        Page<Assignment> assignments = assignmentRepository.findAllByLectureId(lectureId, pageable);
        // Assignment를 AssignmentDto로 전부 변환하여 반환
        return assignments.map(this::convertToDto).getContent();
    }
}
