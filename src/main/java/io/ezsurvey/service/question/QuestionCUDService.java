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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class QuestionCUDService {
	@PersistenceContext
	private EntityManager entityManager;
	
	private final ItemCUDService itemCUDService;
	private final ItemReadService itemReadService;
	private final QuestionRepository questionRepository;
	private final SurveyRepository surveyRepository;
	
	// 문항 추가
	public Map<String, Object> insert(QuestionServiceDTO serviceDTO, List<ItemServiceDTO> itemServiceDTOs, Long surveyId) {
		serviceDTO.setSurvey(surveyRepository.getById(surveyId));
		
		Question question = questionRepository.save(serviceDTO.toEntity());
		
		List<Item> items = itemCUDService.insertList(itemServiceDTOs.stream()
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
		// 원본 설문조사에 포함된 부모 문항 목록 가져오기
		List<Long> originalParentIds = questionRepository.findParentIdBySurveyId(originalSurveyId);
		
		// 개별 부모 문항에 대해
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
					List<Long> originalChildIds = questionRepository.findIdByParentId(originalParentId);
					
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
		// 원본 문항의 PK와 응답 범주 수
		Long originalId = clone.getId();
		Long items = clone.getItems();
		
		// 현재 Entity를 더티 체킹 대상에서 제외
		entityManager.detach(clone);
		
		// 현재 Entity의 값 변경; copy()는 내부적으로 PK를 null로 변경하여 이후 persist()시 새 PK 할당받을 수 있게 함
		clone.copy(survey, parent);

		// 현재 Entity를 영속화(새 PK 할당 및 DB에 삽입)
		entityManager.persist(clone);
	
		// 응답 범주 복제
		if(items>0) itemCUDService.copy(originalId, clone);
	}
	
	public void delete(Long questionId) {
		Question question = questionRepository.findById(questionId).get(); // getById()의 경우 지연 로딩되어 응답 범주 수와 하위 문항 수를 확인할 때마다 SELECT 쿼리 발생
		
		// 자식 문항이 1개 이상 있는 경우
		if(question.getSubquestions()>0) {
			List<Long> childIds = questionRepository.findIdByParentId(questionId); // 자식 문항 PK 목록 조회
			itemCUDService.deleteByQuestionIdIn(childIds); // 자식 문항의 응답 범주를 모두 삭제
			questionRepository.deleteByIdIn(childIds); // 자식 문항을 모두 삭제
		}
		
		// 응답 범주가 1개 이상 있는 경우
		if(question.getItems()>0) {
			itemCUDService.deleteByQuestionId(questionId);
		}
		
		questionRepository.deleteById(questionId);
	}
}
