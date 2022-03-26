package io.ezsurvey.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@Builder @AllArgsConstructor
@NoArgsConstructor
public class SurveyResponseDTO { // 민감한 회원 정보 및 share 링크 값을 제외한 조회 전용 DTO
	private Long survey;
	private String userName;
	private String userProfileURL;
	private String title;
	private String content;
	private String created;
	private String modified;
	private String distributed;
	private String expires;
	private String status;
	private String visibility;
	
	// 테이블에 없는 가상 컬럼
	private Long questions;
	private Long bookmarks;
}
