package io.ezsurvey.dto.survey;

import java.time.LocalDateTime;

import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SurveyAuthDTO {
	private Long surveyId;
	private Long userId;
	private LocalDateTime distributed;
	private LocalDateTime expires;
	private Status status;
	private Visibility visibility;
	private String shared;
}
