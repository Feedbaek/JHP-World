package minskim2.JHP_World.domain.lecture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.repository.LectureRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "LectureService")
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    // TODO: 아래 메소드들 예외 처리 추가
    public void save(Lecture lecture) {
        try {
            lectureRepository.save(lecture);
        } catch (Exception e) {
            log.error("강의 저장 중 오류 발생", e);
            throw new IllegalArgumentException("강의 저장 중 오류 발생");
        }
    }

    public void deleteById(Long lectureId) {
        try {
            lectureRepository.deleteById(lectureId);
        } catch (Exception e) {
            log.error("강의 삭제 중 오류 발생", e);
            throw new IllegalArgumentException("강의 삭제 중 오류 발생");
        }
    }

    public Lecture findById(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
    }

    public Lecture findByName(String name) {
        return lectureRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
    }
}
