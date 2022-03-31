package io.ezsurvey.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// 공통
	INTERNAL_SERVER_ERROR(500, "C001", "서버 내부 오류입니다."),
	HANDLE_ACCESS_DENIED(403, "C002", "권한이 없습니다."),
	METHOD_NOT_ALLOWED(405, "C003", "허용되지 않는 요청 메서드입니다."),
	ENTITY_NOT_FOUND(400, "C004", "대상을 찾을 수 없습니다."),
	
	// Survey
	INVALID_SURVEY_OWNER(403, "S001", "다른 사람의 설문조사를 수정할 수 없습니다."),
	INVALID_SURVEY_STATUS(403, "S002", "배포가 시작된 설문조사를 수정할 수 없습니다."),
	INVALID_SURVEY_VISIBILITY(403, "S003", "공개되지 않은 설문조사입니다."),
	
	// Question
	INVALID_QUESTION_CATEGORY(400, "Q001", "잘못된 문항 유형입니다.")
	;
	
	private final int status;
	private final String code;
	private final String message;
}
