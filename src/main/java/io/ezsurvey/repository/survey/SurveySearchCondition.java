package io.ezsurvey.repository.survey;

import static io.ezsurvey.entity.survey.QSurvey.survey;

import org.apache.commons.lang3.StringUtils; // null인 경우 true, false 반환하기 위해 사용

import com.querydsl.core.types.dsl.BooleanExpression;

import io.ezsurvey.entity.SearchField;

public class SurveySearchCondition {
	public static BooleanExpression contains(SearchField field, String word) {
		if(field==SearchField.TITLE) return containsTitle(word);
		else if(field==SearchField.CONTENT) return containsContent(word);
		else if(field==SearchField.TITLE_OR_CONTENT) return containsTitle(word).or(containsContent(word));
		return null;
	}
	
	private static BooleanExpression containsTitle(String word) {
		return StringUtils.isNotBlank(word) ? survey.title.contains(word) : null;
	}
	
	private static BooleanExpression containsContent(String word) {
		return StringUtils.isNotBlank(word) ? survey.content.contains(word) : null;
	}
}
