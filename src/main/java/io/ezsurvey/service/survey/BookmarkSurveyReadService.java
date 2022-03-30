package io.ezsurvey.service.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.survey.SurveyResponseDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.survey.BookmarkSurveyRepository;
import io.ezsurvey.repository.user.UserRepository;

@Transactional
@Service
public class BookmarkSurveyReadService {
	@Autowired
	private BookmarkSurveyRepository bookmarkSurveyRepository;

	@Autowired
	private UserRepository userRepository;
	
	public Page<SurveyResponseDTO> getByVisibilityAndUser(Long member, SearchField field, String word
			, Pageable pageable) {
		User user = userRepository.getById(member);
		Page<BookmarkSurvey> page = Page.empty();

		page = bookmarkSurveyRepository.findByVisibilityAndUser(user, field, word, pageable);
		
		return page.map(bs -> SurveyResponseDTO.builder()
				.survey(bs.getSurvey().getId())
				.bookmarkId(bs.getId())
				.userName(bs.getUser().getName())
				.title(bs.getSurvey().getTitle())
				.created(bs.getSurvey().getCreated().toString())
				.questions(bs.getSurvey().getQuestions())
				.bookmarks(bs.getSurvey().getBookmarks())
				.build());
	}
	
	public boolean existsBookmark(Long survey, Long member) {
		return bookmarkSurveyRepository.getBySurveyAndUser(survey, member)!=null;
	}
}
