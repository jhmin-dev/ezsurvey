package io.ezsurvey.dto.survey;

import java.time.LocalDateTime;

import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"user"})
@Builder @AllArgsConstructor // @Builder는 인자가 있는 생성자를 요구
@NoArgsConstructor
public class SurveyServiceDTO {
	private Long survey;
	private User user;
	private String title;
	private String content;
	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime distributed;
	private LocalDateTime expires;
	private Status status;
	private Visibility visibility;
	private String shared;
	
	// Entity에 없는 필드
	private long questions;
	private long bookmarks;
	
	// Entity to ServiceDTO
	public SurveyServiceDTO(Survey survey) {
		this.survey = survey.getId();
		this.user = survey.getUser();
		this.title = survey.getTitle();
		this.content = survey.getContent();
		this.created = survey.getCreated();
		this.modified = survey.getModified();
		this.distributed = survey.getDistributed();
		this.expires = survey.getExpires();
		this.status = survey.getStatus();
		this.visibility = survey.getVisibility();
		this.shared = survey.getShared();
	}
	
	// ServiceDTO to Entity
	public Survey toEntity() {
		return Survey.builder()
				.user(user)
				.title(title)
				.content(content)
				.status(status)
				.visibility(visibility)
				.shared(shared)
				.build();
	}
	
	public SurveyServiceDTO updateBookmarks(long bookmarks) {
		this.bookmarks = bookmarks;
		
		return this;
	}
}