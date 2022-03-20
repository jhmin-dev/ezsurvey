package io.ezsurvey.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component // 인증 관련 예외는 DispatcherServlet 이전 필터에서 발생하기 때문에 @ExceptionHandler로 처리할 수 없음
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		request.setAttribute("error", ((OAuth2AuthenticationException)exception).getError());
		request.getRequestDispatcher("/login").forward(request, response);
	}
}
