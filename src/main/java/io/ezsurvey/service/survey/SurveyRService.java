package io.ezsurvey.service.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;

@Transactional
@Service
public class SurveyRService {
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
				.questions(s.getQuestions())
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
				.questions(s.getQuestions())
				.bookmarks(s.getBookmarks())
				.build());
	}

	public SurveyResponseDTO getResponseDTOById(Long survey) {
		SurveyServiceDTO serviceDTO = surveyRepository.getServiceDTOById(survey);
		
		return serviceDTO==null ? null : new SurveyResponseDTO(serviceDTO);
	}
}
