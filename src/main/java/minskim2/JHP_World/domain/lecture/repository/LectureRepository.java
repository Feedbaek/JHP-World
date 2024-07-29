package minskim2.JHP_World.domain.lecture.repository;

import minskim2.JHP_World.domain.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByName(String name);
}
