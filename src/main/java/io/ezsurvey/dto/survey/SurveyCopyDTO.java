package io.ezsurvey.dto.survey;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class SurveyCopyDTO {
	private Long userId;
	@NotBlank @Size(max = (128-6)) // 6은 SurveyCUDAjaxController의 COPY_PREFIX 길이
	private String title;
	@NotBlank
	private String content;
}
