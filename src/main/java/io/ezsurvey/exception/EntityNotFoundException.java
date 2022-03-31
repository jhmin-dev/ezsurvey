package io.ezsurvey.exception;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException() {
		super(ErrorCode.ENTITY_NOT_FOUND);
	}
}
