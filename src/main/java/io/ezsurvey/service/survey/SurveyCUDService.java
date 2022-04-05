package io.ezsurvey.service.survey;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class SurveyCUDService {
	private final SurveyRepository surveyRepository;
	private final UserRepository userRepository;
	
	public Long insert(SurveyServiceDTO serviceDTO, Long userId) {
		serviceDTO.setUser(userRepository.getById(userId));
		
		// 필수 필드에 값 저장
		serviceDTO.setStatus(Status.BEFORE); // 설문조사 생성 시에는 배포 상태가 항상 배포 전으로 설정되어야 함
		if(serviceDTO.getVisibility()==null) { // 사용자가 공개 여부를 별도로 설정하지 않은 경우
			serviceDTO.setVisibility(Visibility.HIDDEN); // 비공개로 설정
		}
		else if(serviceDTO.getVisibility()==Visibility.LINK_ONLY) { // 일부 공개로 설정한 경우
			serviceDTO.setShared(UUID.randomUUID().toString()); // 설문조사 UUID 값을 생성하여 저장
		}
		
		return surveyRepository.save(serviceDTO.toEntity()).getId();
	}
	
	public void update(SurveyServiceDTO serviceDTO) {
		if(serviceDTO.getVisibility()==Visibility.LINK_ONLY 
				&& StringUtils.isBlank(serviceDTO.getShared())) { // 일부 공개로 설정되었고 현재 UUID 값이 없는 경우
			serviceDTO.setShared(UUID.randomUUID().toString()); // 설문조사 UUID 값을 생성하여 저장
		}
		
		surveyRepository.getById(serviceDTO.getSurveyId())
				.update(serviceDTO.getTitle(), serviceDTO.getContent()
						, serviceDTO.getVisibility(), serviceDTO.getShared());
	}
	
	public void delete(Long surveyId) {
		surveyRepository.getById(surveyId).setVisibilityToDeleted();
	}
}
