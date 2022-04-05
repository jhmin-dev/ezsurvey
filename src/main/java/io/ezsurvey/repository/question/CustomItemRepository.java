package io.ezsurvey.repository.question;

import java.util.List;

import io.ezsurvey.dto.question.ItemServiceDTO;

public interface CustomItemRepository {
	List<ItemServiceDTO> findServiceDTOByQuestionId(Long questionId);
}
