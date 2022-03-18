package io.ezsurvey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.ezsurvey.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity // 현재 클래스(=필터)를 스프링 필터 체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Override
	public void configure(WebSecurity web) throws Exception { // WebSecurity 필터가 HttpSecurity 필터보다 빠름
		web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들에 대해서 Security 적용 무시
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/make/**", "/edit/**", "/my/**", "/bookmark/**").authenticated()
				.anyRequest().permitAll()
			.and()
				.logout()
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
			.and()
				.oauth2Login()
				.failureUrl("/login/error") // 로그인 실패시 이동할 URL
				.userInfoEndpoint()
				.userService(customOAuth2UserService); // OAuth2 로그인 성공시 사용자 정보를 처리할 서비스 지정
	}
}