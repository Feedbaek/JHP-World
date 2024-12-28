package minskim2.JHP_World.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.repository.LectureRepository;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.post.dto.PostDto;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.domain.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j(topic = "PostService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    /**
     * CRUD 메서드 구현
     * */
    @Transactional
    public PostDto createPost(PostDto postDto) {
        // 해당 회원이나 강의가 존재하지 않으면 예외 발생
        Member member = memberRepository.findById(postDto.memberId()).orElseThrow(()
                -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Lecture lecture = lectureRepository.findById(postDto.lectureId()).orElseThrow(()
                -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));

        Post post = Post.builder()
                .member(member)
                .lecture(lecture)
                .title(postDto.title())
                .body(postDto.body())
                .build();
        postRepository.save(post);

        return convertToDto(post);
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

    public List<PostDto> findAllByLectureId(Long lectureId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
        Page<Post> postList = postRepository.findAllByLectureId(lectureId, pageable);
        return postList.stream()
                .map(this::convertToDto)
                .toList();
    }
}
