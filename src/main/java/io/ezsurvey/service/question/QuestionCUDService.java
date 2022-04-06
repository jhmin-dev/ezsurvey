package io.ezsurvey.service.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.ChildCopyDTO;
import io.ezsurvey.dto.question.ItemServiceDTO;
import io.ezsurvey.dto.question.ParentCopyDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.entity.question.Item;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.repository.question.QuestionRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionCUDService {
	@PersistenceContext
	private EntityManager entityManager;
	
	private final ItemCUDService itemService;
	private final QuestionRepository questionRepository;
	private final SurveyRepository surveyRepository;
	
	// 문항 추가
	public Map<String, Object> insert(QuestionServiceDTO serviceDTO, List<ItemServiceDTO> itemServiceDTOs, Long surveyId) {
		serviceDTO.setSurvey(surveyRepository.getById(surveyId));
		
		Question question = questionRepository.save(serviceDTO.toEntity());
		
		List<Item> items = itemService.insertList(itemServiceDTOs.stream()
				.map(item -> item.updateQuestion(question)).collect(Collectors.toList()));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", question.getId());
		map.put("items", items.size());
		
		return map;
	}
	
	// 하위 문항 추가
	public Long insert(QuestionServiceDTO serviceDTO, Long surveyId, Long parentId) {
		serviceDTO.setSurvey(surveyRepository.getById(surveyId));
		serviceDTO.setParent(questionRepository.getById(parentId));
		
		return questionRepository.save(serviceDTO.toEntity()).getId();
	}
	
	// 설문조사 단위로 문항 복제
	public void copyAllBySurvey(Long originalSurveyId, Survey cloneSurvey) {
		List<Long> originalParentIds = questionRepository.findParentIdBySurveyId(originalSurveyId);
		
		originalParentIds.stream()
				.map(originalId -> {
					Question clone = questionRepository.findById(originalId).get();
					Long subquestions = clone.getSubquestions(); // PK 새로 할당하기 전 원본의 자식 문항 수
					copyOneBySurvey(cloneSurvey, clone, null);

					return new ParentCopyDTO(clone, originalId, subquestions);
				}) // 부모 문항들을 모두 복제
				.filter(parentCopyDTO -> parentCopyDTO.getSubquestions()>0) // 원본이 자식 문항을 1개 이상 가지고 있는 경우만 남김
				.flatMap(parentCopyDTO -> {
					Long originalParentId = parentCopyDTO.getOriginalParentId();
					List<Long> originalChildIds = questionRepository.findChildIdByParentId(originalParentId);
					
					return originalChildIds.stream()
							.map(originalChildId -> new ChildCopyDTO(originalChildId, parentCopyDTO.getCloneParent()));
				}) // ParentCopyDTO의 스트림을 ChildCopyDTO의 스트림으로 변환(1:N)
				.forEach(childCopyDTO -> {
					Question cloneChild = questionRepository.findById(childCopyDTO.getOriginalChildId()).get();
					copyOneBySurvey(cloneSurvey, cloneChild, childCopyDTO.getCloneParent());
				}); // 자식 문항들을 모두 복제
	}
	
	// 설문조사 단위로 문항 복제
	private void copyOneBySurvey(Survey survey, Question clone, Question parent) {
		entityManager.detach(clone);
		
		clone.copy(survey, parent);

		entityManager.persist(clone);
	
		// 응답 범주 복제
		

	}
}
