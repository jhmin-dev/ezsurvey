package io.ezsurvey.service.survey;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.survey.SurveyCopyDTO;
import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;
import io.ezsurvey.service.question.QuestionCUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class SurveyCUDService {
	@PersistenceContext
	private EntityManager entityManager;
	
	private final QuestionCUDService questionCUDService;
	private final SurveyRepository surveyRepository;
	private final UserRepository userRepository;
	
	public Long insert(SurveyServiceDTO serviceDTO, Long userId) {
		serviceDTO.setUser(userRepository.getById(userId));
		
		// 필수 필드에 값 저장
		serviceDTO.setStatus(Status.BEFORE); // 설문조사 생성 시에는 배포 상태가 항상 배포 전으로 설정되어야 함
		if(serviceDTO.getVisibility()==Visibility.LINK_ONLY) { // 일부 공개로 설정한 경우
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
	
	public Long copy(SurveyCopyDTO copyDTO, Long originalId) {
		/*  
		 * surveyRepository의 getById()는 실제 Entity가 아닌 Entity의 참조(=프록시 객체)를 반환
		 * detach() 후 프록시 객체에 copy() 메서드 사용시 LazyInitializationException: could not initialize proxy – no Session 발생
		 * entityManager를 이용하여 실제 Entity를 불러와야 예외가 발생하지 않음
		 */
		Survey clone = entityManager.find(Survey.class, originalId);
		
		// 현재 Entity를 더티 체킹 대상에서 제외
		entityManager.detach(clone);
		
		// 현재 Entity의 값 변경; copy()는 내부적으로 PK를 null로 변경하여 이후 persist()시 새 PK 할당받을 수 있게 함
		clone.copy(userRepository.getById(copyDTO.getUserId()), copyDTO.getTitle(), copyDTO.getContent());
		
		// 현재 Entity를 영속화(새 PK 할당 및 DB에 삽입)
		entityManager.persist(clone);
		
		// 문항 복제
		questionCUDService.copyAllBySurvey(originalId, clone);
		
		return clone.getId();
	}
}
