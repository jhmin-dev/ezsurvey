package io.ezsurvey.dto.survey;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString(exclude = {"userId"})
@Builder @AllArgsConstructor
@NoArgsConstructor
public class SurveyResponseDTO { // 민감한 회원 정보 및 share 링크 값을 제외한 조회 전용 DTO
	private Long survey;
	private Long bookmarkId;
	private Long userId;
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
	
	// ServiceDTO to ResponseDTO
	public SurveyResponseDTO(SurveyServiceDTO serviceDTO) {
		this.survey = serviceDTO.getSurvey();
		this.userId = serviceDTO.getUser().getId();
		this.userName = serviceDTO.getUser().getName();
		this.userProfileURL = serviceDTO.getUser().getProfileURL();
		this.title = serviceDTO.getTitle();
		this.content = serviceDTO.getContent();
		this.created = serviceDTO.getCreated().toString();
		this.modified = Objects.toString(serviceDTO.getModified());
		this.distributed = Objects.toString(serviceDTO.getDistributed());
		this.expires = Objects.toString(serviceDTO.getExpires());
		this.status = serviceDTO.getStatus().getKey();
		this.visibility = serviceDTO.getVisibility().getKey();
	}
}
