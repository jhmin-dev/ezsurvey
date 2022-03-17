package io.ezsurvey.dto.user;

import java.io.Serializable;

import io.ezsurvey.entity.user.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable { // 세션에 인증된 사용자 정보를 저장하기 위해 직렬화한 클래스
	private Long member;
	private String name;
	private String profileURL;
	
	public SessionUser(User user) {
		this.member = user.getMember();
		this.name = user.getName();
		this.profileURL = user.getProfileURL();
	}
}
