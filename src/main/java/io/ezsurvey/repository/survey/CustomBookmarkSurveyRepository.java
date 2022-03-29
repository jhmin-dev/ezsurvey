package io.ezsurvey.repository.survey;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.user.User;

public interface CustomBookmarkSurveyRepository {
	// 즐겨찾기: 설문조사
	Page<BookmarkSurvey> findByVisibilityAndUser(User user, SearchField field, String word, Pageable pageable);
	
	// 1건 조회
	BookmarkSurvey getBySurveyAndUser(Long survey, Long member);
	
	// 1건 삭제
	Long deleteById(Long bookmark, Long member);
	
	// 선택 항목 삭제
	Long deleteByIdIn(List<Long> bookmark, Long member);
}
