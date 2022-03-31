package io.ezsurvey.exception;

public class DeletedSurveyException extends BusinessException {
	public DeletedSurveyException() {
		super(ErrorCode.DELETED_SURVEY);
	}
}
