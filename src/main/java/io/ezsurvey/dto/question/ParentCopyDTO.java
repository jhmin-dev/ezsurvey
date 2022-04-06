package io.ezsurvey.dto.question;

import io.ezsurvey.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentCopyDTO {
	private Question cloneParent;
	private Long originalParentId;
	private Long subquestions;
}
