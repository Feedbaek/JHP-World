package minskim2.JHP_World.domain.comment.repository;

import minskim2.JHP_World.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Comment> findAllByBoard_Id(Long boardId);
}
