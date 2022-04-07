package io.ezsurvey.entity.user;

import io.ezsurvey.entity.EnumBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeletedAccount implements EnumBase {
	NAME("name", "👻"),
	PROFILE_URL("profileURL", "/images/ghost_flaticon_com_1933233_thumbnail.png");
	
	private final String key;
	private final String name;
}
