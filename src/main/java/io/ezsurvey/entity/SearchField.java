package io.ezsurvey.entity;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchField {
	TITLE("title", "제목"),
	CONTENT("content", "내용"),
	TITLE_OR_CONTENT("title_or_content", "제목+내용"),
	CATEGORY("category", "문항 유형");
	
	public final String key;
	public final String name;
	
	public static SearchField findByKey(String key) {
		return Arrays.stream(SearchField.values())
				.filter(field -> field.getKey().equals(key))
				.findFirst().orElseThrow(() -> new IllegalArgumentException());
	}
}
