package io.ezsurvey.dto.question;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class RequestDTOWrapper {
	@Valid
	private QuestionRequestDTO question;
	@Valid
	private List<ItemRequestDTO> itemList;
	private Long survey;
}
