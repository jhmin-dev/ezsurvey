package io.ezsurvey.exception;

public class InvalidSurveyVisibilityException extends BusinessException {
	public InvalidSurveyVisibilityException() {
		super(ErrorCode.INVALID_SURVEY_VISIBILITY);
	}
}
