package io.ezsurvey.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class UserCUDService {
	private final UserRepository userRepository;
	
	public void delete(Long userId) {
		userRepository.getById(userId).delete();
	}
}
