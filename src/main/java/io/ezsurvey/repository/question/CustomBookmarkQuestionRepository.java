package io.ezsurvey.repository.question;

public interface CustomBookmarkQuestionRepository {
	// 특정 사용자의 모든 즐겨찾기 삭제
	Long deleteByUserId(Long userId);
}
