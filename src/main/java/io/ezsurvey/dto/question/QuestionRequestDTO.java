package io.ezsurvey.dto.question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class QuestionRequestDTO {
	private Long question;
	private Long survey_id;
	private Long user_id;
	private String category;
	private Boolean startFromOne;
	private Long parent_id;
	private Boolean branched;
	private Boolean randomized;
	@Size(max = 256)
	private String varlabel;
	@NotBlank
	private String content;
	private String article;
	@Size(max = 256)
	private String picture;
}
