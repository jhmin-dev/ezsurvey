package io.ezsurvey.repository.question;

import java.util.List;

import io.ezsurvey.dto.question.ItemServiceDTO;

public interface CustomItemRepository {
	// 특정 문항의 응답 범주 목록을 DTO로 조회
	List<ItemServiceDTO> findServiceDTOByQuestionId(Long questionId);
	
	// 특정 문항의 응답 범주 목록을 PK로 조회
	List<Long> findIdByQuestionId(Long questionId);
	
	// 여러 문항의 응답 범주를 일괄 삭제
	Long deleteByQuestionIdIn(List<Long> questionIds);
	
	// 특정 문항의 응답 범주를 일괄 삭제; Spring Data JPA에서 자동 생성하는 쿼리는 SELECT 후 개별 item PK 구해서 하나씩 삭제하는 방식
	Long deleteByQuestionId(Long questionId);
}
