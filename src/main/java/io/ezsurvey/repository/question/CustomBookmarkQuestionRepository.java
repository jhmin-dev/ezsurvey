package io.ezsurvey.repository.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.question.Category;
import io.ezsurvey.entity.user.User;

public interface CustomBookmarkQuestionRepository {
	// 즐겨찾기: 문항
	Page<QuestionPaginationDTO> findPaginationDTOByVisibilityAndUser(User u
			, Category category, SearchField field, String word, Pageable pageable);
	
	// 선택 항목 삭제
	Long deleteByIdIn(List<Long> bookmarkIds);
	
	// 특정 사용자의 모든 즐겨찾기 삭제
	Long deleteByUserId(Long userId);
}
