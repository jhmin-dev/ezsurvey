package io.ezsurvey.entity.survey;

import io.ezsurvey.entity.EnumBaseValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Visibility implements EnumBaseValue {
	PUBLIC("public", "전체 공개", (byte)2),
	LINK_ONLY("link_only", "링크 공개", (byte)1),
	HIDDEN("hidden", "비공개", (byte)0),
	DELETED("deleted", "삭제됨", (byte)-1);
	
	public final String key;
	public final String name;
	public final Byte value;
}
