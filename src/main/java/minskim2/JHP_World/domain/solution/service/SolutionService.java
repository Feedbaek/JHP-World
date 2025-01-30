package minskim2.JHP_World.domain.solution.service;


import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.solution.dto.SolutionDto;
import minskim2.JHP_World.domain.solution.entity.Solution;
import minskim2.JHP_World.domain.solution.repository.SolutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public SolutionDto findById(Long id) {
        return solutionRepository.findById(id)
                .map(SolutionDto::from)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 솔루션을 찾을 수 없습니다.")
                );
    }

    @Transactional
    public SolutionDto save(Long memberId, Long assignmentId, String sourceCode) {

        Member member = Member.ById(memberId);
        Assignment assignment = Assignment.ById(assignmentId);

        return SolutionDto.from(
                solutionRepository.save(
                        Solution.builder()
                                .member(member)
                                .assignment(assignment)
                                .sourceCode(sourceCode)
                                .build()
                )
        );
    }
}

