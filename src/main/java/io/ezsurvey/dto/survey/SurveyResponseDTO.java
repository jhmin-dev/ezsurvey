package io.ezsurvey.dto.survey;

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
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
	
	// Entity에 없는 필드
	private long questions;
	private long bookmarks;
	
	// Setter
	public void setQuestions(long questions) {
		this.questions = questions;
	}
	public void setBookmarks(long bookmarks) {
		this.bookmarks = bookmarks;
	}
	
	// ServiceDTO to ResponseDTO
	public SurveyResponseDTO(SurveyServiceDTO serviceDTO) {
		this.survey = serviceDTO.getSurvey();
		this.userName = serviceDTO.getUser().getName();
		this.userProfileURL = serviceDTO.getUser().getProfileURL();
		this.title = serviceDTO.getTitle();
		this.content = serviceDTO.getContent();
		this.created = serviceDTO.getCreated().toString();
		this.modified = serviceDTO.getModified().toString();
		this.distributed = Objects.toString(serviceDTO.getDistributed(), null);
		this.expires = Objects.toString(serviceDTO.getExpires(), null);
		this.status = serviceDTO.getStatus().getKey();
		this.visibility = serviceDTO.getVisibility().getKey();
		
		this.questions = serviceDTO.getQuestions();
		this.bookmarks = serviceDTO.getBookmarks();
	}
}
