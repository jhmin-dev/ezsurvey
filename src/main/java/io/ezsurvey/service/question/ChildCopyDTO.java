package io.ezsurvey.service.question;

import io.ezsurvey.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChildCopyDTO {
	private Long originalChildId;
	private Question cloneParent;
}
