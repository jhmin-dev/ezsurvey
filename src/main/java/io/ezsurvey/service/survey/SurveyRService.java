package io.ezsurvey.service.survey;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.survey.BookmarkSurveyRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;

@Transactional
@Service
public class SurveyRService {
	private static final Logger logger = LoggerFactory.getLogger(SurveyRService.class);
	
	@Autowired
	private BookmarkSurveyRepository bookmarkSurveyRepository;
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired
	private UserRepository userRepository;

	public Page<SurveyServiceDTO> getSurveyByVisibility(SearchField field, String word
			, Pageable pageable) {
		Page<Survey> page = Page.empty();

		if(field==SearchField.TITLE) { // 제목에서 검색
			page = surveyRepository
					.findByVisibilityAndTitleContaining(Visibility.PUBLIC, word, pageable);
		}
		else if(field==SearchField.CONTENT) { // 설명에서 검색
			page = surveyRepository
					.findByVisibilityAndContentContaining(Visibility.PUBLIC, word, pageable);
		}
		else if(field==SearchField.TITLE_OR_CONTENT) { // 제목+설명에서 검색
			page = surveyRepository.findByTitleOrContent(word, pageable);
		}
		else { // 전체
			page = surveyRepository
					.findByVisibility(Visibility.PUBLIC, pageable);	
		}
		
		return page.map(s -> new SurveyServiceDTO(s)
				.updateBookmarks(bookmarkSurveyRepository.countBySurvey(s)));
	}
	
	public Page<SurveyServiceDTO> getSurveyByUser(Long member, SearchField field, String word
			, Pageable pageable) {
		User user = userRepository.getById(member);
		Page<Survey> page = Page.empty();
		
		if(field==SearchField.TITLE) { // 제목에서 검색
			page = surveyRepository.findByUserAndTitle(user, word, pageable);
		}
		else if(field==SearchField.CONTENT) { // 설명에서 검색
			page = surveyRepository.findByUserAndContent(user, word, pageable);
		}
		else if(field==SearchField.TITLE_OR_CONTENT) { // 제목+설명에서 검색
			page = surveyRepository.findByUserAndTitleOrContent(user, word, pageable);
		}
		else { // 전체
			page = surveyRepository.findByUser(user, pageable);
		}
		
		return page.map(SurveyServiceDTO::new);
	}
	
	public Page<SurveyServiceDTO> getBookmarkSurveyByUser(Long member, SearchField field, String word
			, Pageable pageable) {
		User user = userRepository.getById(member);
		Page<Survey> page = Page.empty();

		if(field==SearchField.TITLE) { // 제목에서 검색
			page = bookmarkSurveyRepository.findSurveyByUserAndTitle(user, word, pageable);
		}
		else if(field==SearchField.CONTENT) { // 설명에서 검색
			page = bookmarkSurveyRepository.findSurveyByUserAndContent(user, word, pageable);
		}
		else if(field==SearchField.TITLE_OR_CONTENT) { // 제목+설명에서 검색
			page = bookmarkSurveyRepository.findSurveyByUserAndTitleOrContent(user, word, pageable);
		}
		else { // 전체
			page = bookmarkSurveyRepository.findSurveyByUser(user, pageable);	
		}
		
		return page.map(s -> new SurveyServiceDTO(s)
				.updateBookmarks(bookmarkSurveyRepository.countBySurvey(s)));
	}
}
