package io.ezsurvey.web.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final Map<String, String> logout = Map.of("result","logout"); // Java 9 이상; 10개 이하의 요소만 가능; unmodifiable
	private final Map<String, String> login = Map.of("result","login");
	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Boolean isAjax = Boolean.parseBoolean(request.getHeader("X-Ajax-Call"));
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("UTF-8");
		
		if(isAjax) { // Ajax로 접근한 경우
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication==null || authentication instanceof AnonymousAuthenticationToken) { // 로그인되어 있지 않으면
				response.getWriter().print(objectMapper.writeValueAsString(logout));
			}
			else {
				response.getWriter().print(objectMapper.writeValueAsString(login));
			}
			response.getWriter().flush();
		}
		else {
			request.getRequestDispatcher("/login").forward(request, response);
		}
	}
	
	// 생성자 방식 의존성 주입
	public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
