package io.ezsurvey.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchField implements EnumBase {
	TITLE("title", "제목"),
	CONTENT("content", "설명"),
	TITLE_OR_CONTENT("title_or_content", "제목+설명"),
	QUESTION_VARLABEL("question_varlabel", "변수명"),
	QUESTION_CONTENT("question_content", "문항"),
	QUESTION_VARLABEL_OR_CONTENT("question_varlabel_content","변수명+문항");
	
	public final String key;
	public final String name;	
}
