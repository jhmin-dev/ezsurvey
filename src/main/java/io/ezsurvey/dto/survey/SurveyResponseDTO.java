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
	private Long surveyId;
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
	private Long bookmarks;
	private Long questions;
	
	// 상세 조회시에만 필요한 가상 컬럼
	private Boolean hasBookmarked;
	
	// ServiceDTO to ResponseDTO
	public SurveyResponseDTO(SurveyServiceDTO serviceDTO) {
		this.surveyId = serviceDTO.getSurveyId();
		this.userId = serviceDTO.getUser().getId();
		this.userName = serviceDTO.getUser().getName();
		this.userProfileURL = serviceDTO.getUser().getProfileURL();
		this.title = serviceDTO.getTitle();
		this.content = serviceDTO.getContent();
		this.created = serviceDTO.getCreated().toString();
		this.modified = Objects.toString(serviceDTO.getModified(), null);
		this.distributed = Objects.toString(serviceDTO.getDistributed(), null);
		this.expires = Objects.toString(serviceDTO.getExpires(), null);
		this.status = serviceDTO.getStatus().getKey();
		this.visibility = serviceDTO.getVisibility().getKey();
		
		// 테이블에 없는 가상 컬럼
		this.bookmarks = serviceDTO.getBookmarks();
		this.questions = serviceDTO.getQuestions();
	}
	
	// PaginationDTO to ResponseDTO
	public SurveyResponseDTO(SurveyPaginationDTO paginationDTO) {
		this.surveyId = paginationDTO.getSurveyId();
		this.bookmarkId = paginationDTO.getBookmarkId();
		this.userName = paginationDTO.getUserName();
		this.userProfileURL = paginationDTO.getUserProfileURL();
		this.title = paginationDTO.getTitle();
		this.created = paginationDTO.getCreated().toString();
		if(paginationDTO.getVisibility()!=null) { // 내 설문조사 목록을 조회하는 경우에만 Not Null
			this.visibility = paginationDTO.getVisibility().getKey();
		}
		
		// 테이블에 없는 가상 컬럼
		this.bookmarks = paginationDTO.getBookmarks();
		this.questions = paginationDTO.getQuestions();
	}
	
	// IndexDTO to ResponseDTO
	public SurveyResponseDTO(SurveyIndexDTO indexDTO) {
		this.surveyId = indexDTO.getSurveyId();
		this.title = indexDTO.getTitle();
		
		// 테이블에 없는 가상 컬럼
		this.questions = indexDTO.getQuestions();
	}

	public void setHasBookmarked(Boolean hasBookmarked) {
		this.hasBookmarked = hasBookmarked;
	}
}
