package io.ezsurvey.dto.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class QuestionIndexDTO {
	private Long questionId;
	private Integer idx; // 설문조사 내 문항 번호
	private Integer subidx; // 문항 내 하위 문항 번호
}
