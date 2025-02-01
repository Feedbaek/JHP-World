package minskim2.JHP_World.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.post.dto.PostDto;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.domain.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.domain.post.dto.PostReq.*;
import static minskim2.JHP_World.domain.post.dto.PostRes.*;

@Service
@Slf4j(topic = "PostService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * CRUD 메서드 구현
     * */
    @Transactional
    public CreateRes createPost(Long memberId, CreateReq postDto) {

        Member member = Member.ById(memberId);
        Lecture lecture = Lecture.ById(postDto.lectureId());

        Post post = Post.builder()
                .member(member)
                .lecture(lecture)
                .title(postDto.title())
                .body(postDto.body())
                .build();
        postRepository.save(post);

        return CreateRes.of(post.getId());
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return convertToDto(post);
    }

    public PostDto convertToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .lectureId(post.getLecture().getId())
                .title(post.getTitle())
                .body(post.getBody())
                .build();
    }

    public Page<Post> findAllByLectureId(Long lectureId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));

        if (lectureId == null) {
            // lectureId가 null이면 전체 게시글 조회
            return postRepository.findAll(pageable);
        }
        return postRepository.findAllByLectureId(lectureId, pageable);
    }
}
