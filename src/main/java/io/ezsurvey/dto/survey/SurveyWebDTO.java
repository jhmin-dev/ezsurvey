package io.ezsurvey.dto.survey;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Visibility;
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
	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime distributed;
	private LocalDateTime expires;
	private String status;
	private String visibility;
	private String shared;
	
	// ServiceDTO to WebDTO
	public SurveyWebDTO(SurveyServiceDTO serviceDTO) {
		this.survey = serviceDTO.getSurvey();
		this.user = serviceDTO.getUser();
		this.title = serviceDTO.getTitle();
		this.content = serviceDTO.getContent();
		this.created = serviceDTO.getCreated();
		this.modified = serviceDTO.getModified();
		this.distributed = serviceDTO.getDistributed();
		this.expires = serviceDTO.getExpires();
		this.status = serviceDTO.getStatus().getKey();
		this.visibility = serviceDTO.getVisibility().getKey();
		this.shared = serviceDTO.getShared();
	}
	
	// WebDTO to ServiceDTO
	public SurveyServiceDTO toServiceDTO() {
		return SurveyServiceDTO.builder()
				.user(user)
				.title(title)
				.content(content)
				.status(EnumBase.findByKey(Status.class, status))
				.visibility(EnumBase.findByKey(Visibility.class, visibility))
				.shared(shared)
				.build();
	}
}
