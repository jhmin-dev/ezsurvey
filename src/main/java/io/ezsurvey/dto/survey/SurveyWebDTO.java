package io.ezsurvey.dto.survey;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.ezsurvey.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"user"})
@NoArgsConstructor
public class SurveyWebDTO {
	private Long survey;
	private User user;
	@NotBlank @Size(max = 128)
	private String title;
	@NotBlank
	private String content;
	private boolean randomized;
	private LocalDateTime registered;
	private LocalDateTime modified;
	private LocalDateTime distributed;
	private LocalDateTime expires;
	private int status;
	private boolean deleted;
	@Min(0) @Max(2)
	private int visibility;
	private String shared;
	
	// ServiceDTO to WebDTO
	public SurveyWebDTO(SurveyServiceDTO serviceDTO) {
		this.survey = serviceDTO.getSurvey();
		this.user = serviceDTO.getUser();
		this.title = serviceDTO.getTitle();
		this.content = serviceDTO.getContent();
		this.randomized = serviceDTO.isRandomized();
		this.registered = serviceDTO.getRegistered();
		this.modified = serviceDTO.getModified();
		this.distributed = serviceDTO.getDistributed();
		this.expires = serviceDTO.getExpires();
		this.status = serviceDTO.getStatus();
		this.deleted = serviceDTO.isDeleted();
		this.visibility = serviceDTO.getVisibility();
		this.shared = serviceDTO.getShared();
	}
	
	// WebDTO to ServiceDTO
	public SurveyServiceDTO toServiceDTO() {
		return SurveyServiceDTO.builder()
				.user(user)
				.title(title)
				.content(content)
				.status(status)
				.visibility(visibility)
				.shared(shared)
				.build();
	}

}
