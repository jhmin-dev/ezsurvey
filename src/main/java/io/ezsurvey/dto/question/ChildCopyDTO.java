package io.ezsurvey.dto.question;

import io.ezsurvey.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChildCopyDTO {
	private Long originalChildId;
	private Question cloneParent;
}
