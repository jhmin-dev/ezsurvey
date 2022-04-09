package io.ezsurvey.service.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.survey.SurveyAuthDTO;
import io.ezsurvey.dto.survey.SurveyIndexDTO;
import io.ezsurvey.dto.survey.SurveyPaginationDTO;
import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class SurveyReadService {
	private final SurveyRepository surveyRepository;
	private final UserRepository userRepository;

	public Page<SurveyPaginationDTO> findPaginationDTOByVisibility(SearchField field, String word, Pageable pageable) {
		return surveyRepository.findPaginationDTOByVisibility(field, word, pageable);
	}

	public Page<SurveyPaginationDTO> findPaginationDTOByUserId(Long userId, SearchField field, String word, Pageable pageable) {
		User user = userRepository.getById(userId);
		
		return surveyRepository.findPaginationDTOByUser(user, field, word, pageable);
	}

	public SurveyServiceDTO getServiceDTOById(Long surveyId) {
		return surveyRepository.getServiceDTOById(surveyId);
	}
	
	public SurveyIndexDTO getIndexDTOById(Long surveyId) {
		return surveyRepository.getIndexDTOById(surveyId);
	}
	
	public SurveyAuthDTO getAuthDTOById(Long surveyId) {
		return surveyRepository.getAuthDTOById(surveyId);
	}
	
	public Long getUserIdById(Long surveyId) {
		return surveyRepository.getUserIdById(surveyId);
	}
}
