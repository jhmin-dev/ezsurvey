package io.ezsurvey.exception;

public class InvalidSurveyStatusException extends BusinessException {
	public InvalidSurveyStatusException() {
		super(ErrorCode.INVALID_SURVEY_STATUS);
	}
}
