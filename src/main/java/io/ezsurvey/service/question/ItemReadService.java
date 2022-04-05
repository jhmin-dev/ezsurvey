package io.ezsurvey.service.question;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.ItemServiceDTO;
import io.ezsurvey.repository.question.ItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class ItemReadService {
	private final ItemRepository itemRepository;
	
	public List<ItemServiceDTO> findServiceDTOByQuestionId(Long questionId) {
		return itemRepository.findServiceDTOByQuestionId(questionId);
	}
}
