package io.ezsurvey.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.repository.user.UserRepository;
import io.ezsurvey.service.question.BookmarkQuestionCUDService;
import io.ezsurvey.service.survey.BookmarkSurveyCUDService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class UserCUDService {
	private final UserRepository userRepository;
	private final BookmarkSurveyCUDService bookmarkSurveyCUDService;
	private final BookmarkQuestionCUDService bookmarkQuestionCUDService;
	
	public void delete(Long userId) {
		// 즐겨찾기한 설문조사와 문항을 모두 삭제
		bookmarkSurveyCUDService.deleteByUserId(userId);
		bookmarkQuestionCUDService.deleteByUserId(userId);
		
		// 탈퇴 계정으로 변경
		userRepository.getById(userId).delete();
	}
}
