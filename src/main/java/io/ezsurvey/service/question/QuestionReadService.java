package io.ezsurvey.service.question;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.repository.question.QuestionRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionReadService {
	private final QuestionRepository questionRepository;
	
	public List<QuestionPaginationDTO> findBySurvey(Long survey, Long question, int pageSize) {
		List<Question> list = questionRepository.findBySurvey(survey, question, pageSize);
		
		return list.stream().map(q -> QuestionPaginationDTO.builder()
				.question(q.getId())
				.category(q.getCategory().getKey())
				.parent(Optional.ofNullable(q.getParent()).map(Question::getId).orElse(null))
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
	
	public boolean existsBySuveyAndId(Long survey, Long question) {
		return questionRepository.existsBySurveyIdAndIdGreaterThan(survey, question);
	}
}
