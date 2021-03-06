package io.ezsurvey.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.ezsurvey.service.user.CustomOAuth2UserService;
import io.ezsurvey.web.user.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 초기화되지 않은 final 필드와 @NonNull이 적용된 필드에 대한 생성자
@Configuration
@EnableWebSecurity // 현재 클래스(=필터)를 스프링 필터 체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception { // WebSecurity 필터가 HttpSecurity 필터보다 빠름
		web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들에 대해서 Security 적용 무시
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.ignoringAntMatchers("/ajax/**") // Ajax 요청에 대해 CSRF 적용하지 않음
				.and()
			.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/make/**", "/edit/**", "/my/**", "/bookmark/**", "/settings/**", "/delete/**").authenticated()
				.anyRequest().permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 요청 URL
				.logoutSuccessUrl("/") // 로그아웃 성공시 이동할 URL
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
			.exceptionHandling()
				.accessDeniedPage("/login") // 권한이 없는 경우 이동할 URL
				.and()
			.oauth2Login()
				.loginPage("/login") // 인증 요구시 이동할 URL
				.failureHandler(customAuthenticationFailureHandler) // 로그인 실패를 처리할 핸들러 지정
				.userInfoEndpoint()
				.userService(customOAuth2UserService); // OAuth2 로그인 성공시 사용자 정보를 처리할 서비스 지정
	}
}
