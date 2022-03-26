package io.ezsurvey.service.survey;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
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

	public Page<SurveyResponseDTO> getSurveyByVisibility(SearchField field, String word
			, Pageable pageable) {
		Page<Survey> page = Page.empty();

		page = surveyRepository.findByVisibility(field, word, pageable);
		
		return page.map(s -> SurveyResponseDTO.builder()
				.survey(s.getId())
				.userName(s.getUser().getName())
				.title(s.getTitle())
				.created(s.getCreated().toString())
				.bookmarks(s.getBookmarks())
				.build());
	}

	public Page<SurveyResponseDTO> getSurveyByUser(Long member, SearchField field, String word
			, Pageable pageable) {
		User user = userRepository.getById(member);
		Page<Survey> page = Page.empty();
		
		page = surveyRepository.findByUser(user, field, word, pageable);
		
		return page.map(s -> SurveyResponseDTO.builder()
				.survey(s.getId())
				.title(s.getTitle())
				.created(s.getCreated().toString())
				.visibility(s.getVisibility().getKey())
				.bookmarks(s.getBookmarks())
				.build());
	}

	public Page<SurveyResponseDTO> getByVisibilityAndUser(Long member, SearchField field, String word
			, Pageable pageable) {
		User user = userRepository.getById(member);
		Page<Survey> page = Page.empty();

		page = bookmarkSurveyRepository.findByVisibilityAndUser(user, field, word, pageable);
		
		return page.map(s -> SurveyResponseDTO.builder()
				.survey(s.getId())
				.userName(s.getUser().getName())
				.title(s.getTitle())
				.created(s.getCreated().toString())
				.bookmarks(s.getBookmarks())
				.build());
	}
}
