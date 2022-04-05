package io.ezsurvey.dto.user;

import java.io.Serializable;

import io.ezsurvey.entity.user.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SessionUser implements Serializable {
	// 생략시 Redis에 세션을 저장하는 경우 SerializationException: Cannot deserialize 발생
	private static final long serialVersionUID = 4286759114371999351L;
	
	// 세션에 인증된 사용자 정보를 저장하기 위해 직렬화한 클래스
	private Long userId;
	private String name;
	private String profileURL;
	
	// Entity to DTO
	public SessionUser(User user) {
		this.userId = user.getId();
		this.name = user.getName();
		this.profileURL = user.getProfileURL();
	}
}
