package io.ezsurvey.service.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.survey.SurveyPaginationDTO;
import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.survey.BookmarkSurveyRepository;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class BookmarkSurveyReadService {
	private final BookmarkSurveyRepository bookmarkSurveyRepository;
	private final UserRepository userRepository;
	
	public Page<SurveyPaginationDTO> getByVisibilityAndUser(Long userId, SearchField field, String word
			, Pageable pageable) {
		User user = userRepository.getById(userId);

		return bookmarkSurveyRepository.findByVisibilityAndUser(user, field, word, pageable);
	}
	
	public boolean existsBookmark(Long surveyId, Long userId) {
		return bookmarkSurveyRepository.existsBySurveyIdAndUserId(surveyId, userId);
	}
}
