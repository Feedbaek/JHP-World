package minskim2.JHP_World.domain.assignment.service;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
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
    public Assignment createAssignment(AssignmentDto assignmentDto) {
        // 해당 강의가 존재하지 않으면 예외 발생
        Lecture lecture = lectureRepository.findById(assignmentDto.getLectureId()).orElseThrow(()
                -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
        // Assignment 생성
        Assignment assignment = Assignment.builder()
                .title(assignmentDto.getTitle())
                .body(assignmentDto.getBody())
                .lecture(lecture)
                .build();
        return assignmentRepository.save(assignment);
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
    public List<AssignmentDto> getDtoListByLectureId(Long lectureId, @Positive int page, int size) {

        // 페이지 번호와 사이즈로 Pageable 객체 생성
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdDate"));
        Page<Assignment> assignments = assignmentRepository.findAllByLectureId(lectureId, pageable);
        // Assignment를 AssignmentDto로 전부 변환하여 반환
        return assignments.map(this::convertToDto).getContent();
    }

    /**
     * Assignment ID로 해당 Assignment를 조회하는 메소드
     * */
    public AssignmentDto findById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(()
                -> new IllegalArgumentException("해당 과제가 존재하지 않습니다."));
        return convertToDto(assignment);
    }


    // 위에 메소드들은 Deprecated 될 예정입니다.

    /**
     * Lecture ID로 해당 강의의 Assignment 목록을 조회하는 메소드
     * */
    public List<AssignmentDto> getAssignmentListByLectureId(Long lectureId, int page) {
         return assignmentQueryRepository.findListByLectureId(lectureId, page).stream()
                 .map(AssignmentDto::from)
                 .toList();
    }
}
