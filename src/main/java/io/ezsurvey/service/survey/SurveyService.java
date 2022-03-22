package io.ezsurvey.service.survey;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.repository.survey.SurveyRepository;

@Service
public class SurveyService {
	@Autowired
	private SurveyRepository surveyRepository;
	
	public Survey insertSurvey(SurveyServiceDTO serviceDTO) {
		if(serviceDTO.getVisibility()==1) { // 일부 공개로 설정한 경우
			serviceDTO.setShared(UUID.randomUUID().toString()); // 설문조사 UUID 값을 생성하여 serviceDTO에 저장
		}
		
		return surveyRepository.save(serviceDTO.toEntity());
	}
}
