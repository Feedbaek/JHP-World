package minskim2.JHP_World.domain.post.repository;

import minskim2.JHP_World.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByLectureId(Long LectureId, Pageable pageable);

    int countByLectureId(Long LectureId);

    @EntityGraph(attributePaths = {"member"})
    Optional<Post> findPostAndMemberById(Long id);
}
