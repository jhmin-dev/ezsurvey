package io.ezsurvey.service.user;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ezsurvey.dto.user.OAuth2Attributes;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.user.Provider;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@Transactional
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final HttpSession session;
	
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
		
		// OAuth2 인증을 제공하는 서비스명; google, kakao, naver
		Provider provider = EnumBase.findByKey(Provider.class, userRequest.getClientRegistration()
				.getRegistrationId());
		
		// OAuth2 인증시 key 값; {google="sub", kakao="id", naver="response"}
		String userNameAttribute = userRequest.getClientRegistration()
				.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		// OAuth2 인증을 통해 가져온 OAuth2User의 attribute를 담는 of 메서드
		OAuth2Attributes attributes = OAuth2Attributes.of(provider, userNameAttribute, oAuth2User.getAttributes());
		
		// 제공 정보를 확인하기 위해 콘솔에 출력
		log.info(attributes.getAttributes().toString());
		
		// 데이터베이스에 사용자 정보가 없으면 회원 가입, 있으면 로그인
		User user = saveOrUpdate(attributes);
		
		// 세션에 사용자 정보를 저장
		session.setAttribute("user", new SessionUser(user));
		
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
				, attributes.getAttributes(), attributes.getUserNameAttribute());
	}

	private User saveOrUpdate(OAuth2Attributes attributes) throws OAuth2AuthenticationException {
		// 이메일 주소로 데이터베이스에서 사용자 검색
		User user = userRepository.findByEmail(attributes.getEmail())
				.map(entity -> entity.update(attributes.getEmailVerified(), attributes.getName(), attributes.getProfileURL())) // 데이터베이스에 사용자 정보가 있는 경우, 해당 정보에서 이메일 인증 여부, 이름, 프로필 이미지를 현재 OAuth2 인증을 통해 가져온 정보로 갱신 
				.orElse(attributes.toEntity()); // 데이터베이스에 사용자 정보가 없는 경우, 현재 OAuth2 인증을 통해 가져온 정보를 User 객체로 변환
		
		// 현재 OAuth2 인증을 제공한 서비스명이 데이터베이스에 저장된 서비스명과 일치하는지 검증
		if(user.getProvider()==attributes.getProvider()) {
			// 데이터베이스에 사용자를 INSERT 또는 UPDATE
			return userRepository.save(user);
		}
		else { // Kakao 계정 대표 이메일로 gmail을 설정한 사용자가 Kakao로 회원 가입 후 Google로 로그인하는 상황을 방지
			throw new ProviderMismatchException();
		}
	}
}
