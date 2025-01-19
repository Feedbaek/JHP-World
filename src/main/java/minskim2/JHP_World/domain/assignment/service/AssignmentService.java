package minskim2.JHP_World.domain.assignment.service;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.dto.AssignmentQ;
import minskim2.JHP_World.domain.assignment.dto.AssignmentReq;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.assignment.repository.AssignmentQueryRepository;
import minskim2.JHP_World.domain.assignment.repository.AssignmentRepository;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.repository.LectureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
@Slf4j(topic = "AssignmentService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentQueryRepository assignmentQueryRepository;
    private final LectureRepository lectureRepository;

    /**
     * Assignment 엔티티를 AssignmentDto로 변환하는 메소드
     * */
    public AssignmentDto convertToDto(Assignment assignment) {
        return AssignmentDto.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .body(assignment.getBody())
                .lectureId(assignment.getLecture().getId())
                .build();
    }

    // TODO: 아래 메소드들 커스텀 예외 처리 추가
    /**
     * Assignment 저장 메소드
     * */
    @Transactional
    public Long createAssignment(AssignmentReq.Create assignmentDto) {

        Lecture lecture = Lecture.ById(assignmentDto.lectureId());

        return assignmentRepository.save(Assignment.builder()
                .title(assignmentDto.title())
                .body(assignmentDto.body())
                .lecture(lecture)
                .build()).getId();
    }

    /**
     * Assignment 수정 메소드
     * */
    @Transactional
    public Long updateAssignment(AssignmentReq.Update assignmentDto) {
        Assignment assignment = assignmentRepository.findById(assignmentDto.id()).orElseThrow(()
                -> new IllegalArgumentException("해당 과제가 존재하지 않습니다."));

        assignment.update(assignmentDto);

        return assignment.getId();
    }

    /**
     * Assignment 삭제 메소드
     * */
    public void deleteById(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

    /**
     * 강의 ID로 해당 강의의 Assignment 목록을 조회하는 메소드
     * @param lectureId 강의 ID
     * @param page 페이지 번호. 1 이상 자연수
     * */
    public Page<Assignment> getDtoListByLectureId(Long lectureId, @Positive int page, int size) {

        // 페이지 번호와 사이즈로 Pageable 객체 생성
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdDate").descending());

        return assignmentRepository.findAllByLectureId(lectureId, pageable);
    }

    /**
     * Assignment ID로 해당 Assignment를 조회하는 메소드
     * */
    public AssignmentDto findById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(()
                -> new IllegalArgumentException("해당 과제가 존재하지 않습니다."));
        return convertToDto(assignment);
    }


    /**
     * Lecture ID로 해당 강의의 Assignment 목록을 조회하는 메소드
     * */
    public Page<AssignmentQ> getAssignmentListByLectureId(Long lectureId, @Positive int page) {

        int pageNumber = page - 1;
        return assignmentQueryRepository.findListByLectureId(lectureId, pageNumber);
    }

    /**
     * Assignment 삭제 메소드
     * */
    public Long deleteAssignment(AssignmentReq.Delete req) {

        assignmentRepository.deleteById(req.id());
        return req.id();
    }
}
