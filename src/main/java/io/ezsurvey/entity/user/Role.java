package io.ezsurvey.entity.user;

import io.ezsurvey.entity.EnumBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumBase {
	GUEST("ROLE_GUEST", "비회원"),
	USER("ROLE_USER", "사용자"),
	ADMIN("ROLE_ADMIN", "관리자");
	
	private final String key;
	private final String name;
}
