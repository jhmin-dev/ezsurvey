package io.ezsurvey.repository.survey;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.dto.survey.SurveyPaginationDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.user.User;

public interface CustomBookmarkSurveyRepository {
	// 즐겨찾기: 설문조사
	Page<SurveyPaginationDTO> findPaginationDTOByVisibilityAndUser(User u, SearchField field, String word, Pageable pageable);
	
	// 1건 조회
	BookmarkSurvey getBySurveyIdAndUserId(Long surveyId, Long userId);
	
	// 선택 항목 삭제
	Long deleteByIdIn(List<Long> bookmarkIds);
	
	// 특정 사용자의 모든 즐겨찾기 삭제
	Long deleteByUserId(Long userId);
}
