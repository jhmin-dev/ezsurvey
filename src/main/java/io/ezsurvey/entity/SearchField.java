package io.ezsurvey.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchField implements EnumBase {
	TITLE("title", "제목"),
	CONTENT("content", "설명"),
	TITLE_OR_CONTENT("title_or_content", "제목+설명"),
	QUESTION_SURVEY("question_survey", "설문조사 제목"),
	QUESTION_CONTENT("question_content", "문항 내용");
	
	public final String key;
	public final String name;	
}
