package io.ezsurvey.service.user;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class ProviderMismatchException extends OAuth2AuthenticationException {
	public ProviderMismatchException() {
		super(new OAuth2Error("provider_mismatch", "다른 소셜 플랫폼으로 이미 가입한 이메일 주소입니다!", "/login"));
	}
}
