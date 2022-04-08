package io.ezsurvey.service.question;

import io.ezsurvey.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParentCopyDTO {
	private Question cloneParent;
	private Long originalParentId;
	private Long subquestions;
}
