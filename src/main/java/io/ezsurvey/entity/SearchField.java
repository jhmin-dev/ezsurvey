package io.ezsurvey.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchField implements EnumBase {
	TITLE("title", "제목"),
	CONTENT("content", "설명"),
	TITLE_OR_CONTENT("title_or_content", "제목+설명"),
	CATEGORY("category", "문항 유형"),
	QUESTION("question", "문항");
	
	public final String key;
	public final String name;	
}
