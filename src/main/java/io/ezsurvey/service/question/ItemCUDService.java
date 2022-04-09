package io.ezsurvey.service.question;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.ItemServiceDTO;
import io.ezsurvey.entity.question.Item;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.repository.question.ItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class ItemCUDService {
	@PersistenceContext
	private EntityManager entityManager;
	
	private final ItemRepository itemRepository;
	
	// 응답 범주 추가
	public List<Item> insert(List<ItemServiceDTO> serviceDTOs) {
		return serviceDTOs.stream().map(item -> itemRepository.save(item.toEntity())).collect(Collectors.toList());
	}
	
	// 문항 단위로 응답 범주 복제
	public void copy(Long originalQuestionId, Question cloneQuestion) {
		// 원본 문항에 포함된 응답 범주 목록 가져오기
		List<Long> originalIds = itemRepository.findIdByQuestionId(originalQuestionId);
		
		// 개별 응답 범주에 대해
		originalIds.stream().forEach(originalId -> {
			Item clone = itemRepository.findById(originalId).get();
			
			// 현재 Entity를 더티 체킹 대상에서 제외
			entityManager.detach(clone);
			
			// 현재 Entity의 값 변경; copy()는 내부적으로 PK를 null로 변경하여 이후 persist()시 새 PK 할당받을 수 있게 함
			clone.copy(cloneQuestion);
			
			// 현재 Entity를 영속화(새 PK 할당 및 DB에 삽입)
			entityManager.persist(clone);
		});
	}
	
	// 특정 문항의 응답 범주 삭제
	public Long deleteByQuestionId(Long questionId) {
		return itemRepository.deleteByQuestionId(questionId);
	}
	
	// 여러 문항의 응답 범주 일괄 삭제
	public Long deleteByQuestionIdIn(List<Long> questionIds) {
		return itemRepository.deleteByQuestionIdIn(questionIds);
	}
}
