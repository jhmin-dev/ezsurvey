package io.ezsurvey.repository.question;

import static io.ezsurvey.entity.question.QQuestion.question;

import org.apache.commons.lang3.StringUtils; // null인 경우 true, false 반환하기 위해 사용

import com.querydsl.core.types.dsl.BooleanExpression;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.question.Category;

public class QuestionSearchCondition {
	public static BooleanExpression contains(SearchField field, String word) {
		if(StringUtils.isNotBlank(word)) {
			if(field==SearchField.QUESTION_VARLABEL) return containsVarlabel(word);
			else if(field==SearchField.QUESTION_CONTENT) return containsContent(word);
			else if(field==SearchField.QUESTION_VARLABEL_OR_CONTENT) return containsVarlabel(word).or(containsContent(word));
		}
		return null;
	}
	
	private static BooleanExpression containsVarlabel(String word) {
		return question.varlabel.containsIgnoreCase(word);
	}
	
	private static BooleanExpression containsContent(String word) {
		return question.content.containsIgnoreCase(word);
	}
	
	public static BooleanExpression eqCategory(Category category) {
		return category==null ? null : question.category.eq(category);
	}
}
