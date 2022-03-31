package io.ezsurvey.service.question;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.ItemServiceDTO;
import io.ezsurvey.entity.question.Item;
import io.ezsurvey.repository.question.ItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class ItemCUDService {
	private final ItemRepository itemRepository;
	
	// 응답 범주 추가
	public List<Item> insertList(List<ItemServiceDTO> serviceDTOs) {
		return serviceDTOs.stream().map(item -> itemRepository.save(item.toEntity())).collect(Collectors.toList());
	}
}
