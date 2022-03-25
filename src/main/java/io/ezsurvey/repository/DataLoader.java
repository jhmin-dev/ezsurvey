package io.ezsurvey.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.ezsurvey.entity.question.Category;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.repository.question.CategoryRepository;
import io.ezsurvey.repository.survey.BookmarkSurveyRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired
	private BookmarkSurveyRepository bookmarkSurveyRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*
		String[] categories = {"서답형", "선다형", "척도형"};
		for(String s: categories) {
			Category c = categoryRepository.findByName(s).orElse(new Category(s));
			categoryRepository.save(c);
		}
		*/
		/*
		for(int i=0;i<13;i++) {
			Survey survey = Survey.builder()
					.user(userRepository.getById(2L))
					.title("테스트용 설문조사" + i)
					.content("테스트용 설문조사")
					.status(Status.BEFORE)
					.visibility(Visibility.PUBLIC)
					.build();
			surveyRepository.save(survey);
		}
		*/
		/*
		bookmarkSurveyRepository.save(BookmarkSurvey.builder()
				.survey(surveyRepository.getById(2L))
				.user(userRepository.getById(2L))
				.build());
		*/
	}

}
