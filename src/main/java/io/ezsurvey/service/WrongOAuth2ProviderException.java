package io.ezsurvey.service;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class WrongOAuth2ProviderException extends OAuth2AuthenticationException {
	public WrongOAuth2ProviderException() {
		super(new OAuth2Error("invalid_request", "다른 소셜 플랫폼으로 이미 가입한 이메일 주소입니다!", "/login"));
	}
}
