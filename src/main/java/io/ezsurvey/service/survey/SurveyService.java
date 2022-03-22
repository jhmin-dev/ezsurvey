package io.ezsurvey.service.survey;

import java.util.UUID;

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
import io.ezsurvey.repository.survey.SurveyRepository;

@Transactional
@Service
public class SurveyService {
	private static final Logger logger = LoggerFactory.getLogger(SurveyService.class);
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	public Survey insertSurvey(SurveyServiceDTO serviceDTO) {
		if(serviceDTO.getVisibility()==Visibility.LINK_ONLY) { // 일부 공개로 설정한 경우
			serviceDTO.setShared(UUID.randomUUID().toString()); // 설문조사 UUID 값을 생성하여 serviceDTO에 저장
		}
		
		return surveyRepository.save(serviceDTO.toEntity());
	}
	
	public Page<SurveyServiceDTO> find(Pageable pageable, SearchField field, String word) {
		Page<Survey> list = null;

		if(field==SearchField.TITLE) {
			list = surveyRepository.findByVisibilityAndTitleContaining(Visibility.PUBLIC, word, pageable);
		}
		else if(field==SearchField.CONTENT) {
			list = surveyRepository.findByVisibilityAndContentContaining(Visibility.PUBLIC, word, pageable);
		}
		else if(field==SearchField.TITLE_OR_CONTENT) {
			list = surveyRepository.findByVisibilityAndTitleContainingOrVisibilityAndContentContaining(Visibility.PUBLIC, word, Visibility.PUBLIC, word, pageable);
		}
		else {
			list = surveyRepository.findByVisibility(Visibility.PUBLIC, pageable);	
		}
		
		return list.map(SurveyServiceDTO::new);
	}
}
