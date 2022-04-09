package io.ezsurvey.service.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.question.QuestionPaginationDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.question.Category;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.question.BookmarkQuestionRepository;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkQuestionReadService {
	private final BookmarkQuestionRepository bookmarkQuestionRepository;
	private final UserRepository userRepository;
	
	public Page<QuestionPaginationDTO> findPaginationDTOByVisibilityAndUser(Long userId
			, Category category, SearchField field, String word, Pageable pageable) {
		User user = userRepository.getById(userId);
		
		return bookmarkQuestionRepository.findPaginationDTOByVisibilityAndUser(user, category, field, word, pageable);
	}
}
