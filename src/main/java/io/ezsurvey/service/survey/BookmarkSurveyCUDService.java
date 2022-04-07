package io.ezsurvey.service.survey;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.repository.survey.BookmarkSurveyRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class BookmarkSurveyCUDService {
	private final BookmarkSurveyRepository bookmarkSurveyRepository;
	private final SurveyRepository surveyRepository;
	private final UserRepository userRepository;
	
	public Long getBookmark(Long surveyId, Long userId) {
		BookmarkSurvey bookmarkSurvey = bookmarkSurveyRepository.getBySurveyAndUser(surveyId, userId);
		
		return bookmarkSurvey==null ? null : bookmarkSurvey.getId();
	}
	
	public Long insertBookmark(Long surveyId, Long userId) {
		return bookmarkSurveyRepository.save(BookmarkSurvey.builder()
				.survey(surveyRepository.getById(surveyId))
				.user(userRepository.getById(userId)).build()).getId();
	}
	
	public void deleteBookmark(Long bookmarkId) {
		bookmarkSurveyRepository.deleteById(bookmarkId);
	}
	
	public Long deleteBookmarksByIdIn(List<Long> bookmarkIds) {
		return bookmarkSurveyRepository.deleteByIdIn(bookmarkIds);
	}
}
