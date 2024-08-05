package minskim2.JHP_World.domain.post.repository;

import minskim2.JHP_World.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByLectureId(Long assignmentId, Pageable pageable);

    int countByLectureId(Long LectureId);
}
