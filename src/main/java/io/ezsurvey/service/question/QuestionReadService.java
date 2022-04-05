package io.ezsurvey.service.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.repository.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionReadService {
	private final QuestionRepository questionRepository;
	
	public List<QuestionPaginationDTO> findBySurvey(Long surveyId, Long questionId, int pageSize) {
		List<Question> list = questionRepository.findBySurvey(surveyId, questionId, pageSize);
		
		return list.stream().map(q -> QuestionPaginationDTO.builder()
				.questionId(q.getId())
				.category(q.getCategory().getKey())
				.parentId(Optional.ofNullable(q.getParent()).map(Question::getId).orElse(null))
				.branched(q.getBranched())
				.randomized(q.getRandomized())
				.idx(q.getIdx())
				.subidx(q.getSubidx())
				.varlabel(q.getVarlabel())
				.content(q.getContent())
				.items(q.getItems())
				.subquestions(q.getSubquestions())
				.build()).toList();
	}
	
	public boolean existsBySuveyAndId(Long surveyId, Long questionId) {
		return questionRepository.existsBySurveyIdAndIdGreaterThan(surveyId, questionId);
	}
}
