package io.ezsurvey.service.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.repository.survey.BookmarkSurveyRepository;
import io.ezsurvey.repository.survey.SurveyRepository;
import io.ezsurvey.repository.user.UserRepository;

@Transactional
@Service
public class BookmarkSurveyCUDService {
	@Autowired
	private BookmarkSurveyRepository bookmarkSurveyRepository;
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Long getBookmark(Long survey, Long member) {
		BookmarkSurvey bookmarkSurvey = bookmarkSurveyRepository.getBySurveyAndUser(survey, member);
		
		return bookmarkSurvey==null ? null : bookmarkSurvey.getId();
	}
	
	public Long insertBookmark(Long survey, Long member) {
		return bookmarkSurveyRepository.save(BookmarkSurvey.builder()
				.survey(surveyRepository.getById(survey))
				.user(userRepository.getById(member)).build()).getId();
	}
	
	public Long deleteBookmark(Long bookmark, Long member) {
		return bookmarkSurveyRepository.deleteById(bookmark, member);
	}
	
	public Long deleteBookmarksByIdIn(List<Long> bookmarks, Long member) {
		return bookmarkSurveyRepository.deleteByIdIn(bookmarks, member);
	}
}
