package io.ezsurvey.dto.survey;

import java.time.LocalDateTime;

import io.ezsurvey.entity.survey.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString(exclude = {"userProfileURL"})
@NoArgsConstructor
public class SurveyPaginationDTO {
	private Long surveyId;
	private Long bookmarkId;
	private String userName;
	private String userProfileURL;
	private String title;
	private LocalDateTime created;
	private Visibility visibility;
	
	// 테이블에 없는 가상 컬럼
	private Long bookmarks;
	private Long questions;
}
