package io.ezsurvey.dto.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
public class SurveyIndexDTO {
	private Long surveyId;
	private String title;
	
	// 테이블에 없는 가상 컬럼
	private Long questions;
}
