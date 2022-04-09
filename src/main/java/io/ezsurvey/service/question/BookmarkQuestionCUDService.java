package io.ezsurvey.service.question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.repository.question.BookmarkQuestionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class BookmarkQuestionCUDService {
	private final BookmarkQuestionRepository bookmarkQuestionRepository;
	
	public Long deleteByUserId(Long userId) {
		return bookmarkQuestionRepository.deleteByUserId(userId);
	}
}
