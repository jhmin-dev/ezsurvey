package io.ezsurvey.entity.user;

import io.ezsurvey.entity.EnumBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider implements EnumBase {
	GOOGLE("google", "구글"),
	KAKAO("kakao", "카카오"),
	NAVER("naver", "네이버");
	
	public final String key;
	public final String name;
}
