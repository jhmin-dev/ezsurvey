package io.ezsurvey.exception;

public class InvalidSurveyOwnerException extends BusinessException {
	public InvalidSurveyOwnerException() {
		super(ErrorCode.INVALID_SURVEY_OWNER);
	}
}
