package io.ezsurvey.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchField implements EnumBase {
	TITLE("title", "제목"),
	CONTENT("content", "내용"),
	TITLE_OR_CONTENT("title_or_content", "제목+내용"),
	CATEGORY("category", "문항 유형");
	
	public final String key;
	public final String name;	
}
