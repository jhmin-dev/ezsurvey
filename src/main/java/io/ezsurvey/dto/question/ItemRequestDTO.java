package io.ezsurvey.dto.question;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class ItemRequestDTO {
	private Long item;
	private Long question_id;
	@NotNull
	private Integer value;
	@Size(max = 256)
	private String vallabel;
	@Size(max = 256)
	private String picture;
}
