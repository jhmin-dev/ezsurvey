package io.ezsurvey.repository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.ezsurvey.entity.question.Category;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.repository.question.QuestionRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Component
public class DataLoader implements ApplicationRunner {
	private final UserRepository userRepository;
	private final SurveyRepository surveyRepository;
	private final QuestionRepository questionRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 하위 문항 실험용
		/*
		for(int i=0;i<2;i++) {
			Question question = Question.builder()
					.survey(surveyRepository.getById(46L))
					.category(Category.SHORT_ANSWER)
					.parent(questionRepository.getById(49L))
					.content("하위 문항을 실험 중입니다" + i)
					.build();
			questionRepository.save(question);
		}
		*/
		
		/*
		for(int i=0;i<13;i++) {
			Survey survey = Survey.builder()
					.user(userRepository.getById(1L))
					.title("다시 전체 공개" + i)
					.content("검색할 거라고~~")
					.status(Status.BEFORE)
					.visibility(Visibility.PUBLIC)
					.build();
			surveyRepository.save(survey);
		}
		*/
	}
}
