package io.ezsurvey.dto.question;

import io.ezsurvey.entity.question.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionPaginationDTO {
	private Long questionId;
	private Long bookmarkId;
	private Long surveyId;
	private Category category;
	private Boolean branched;
	private Boolean randomized;
	private Integer idx;
	private Integer subidx;
	private String varlabel;
	
	// 테이블에 없는 가상 컬럼
	private Long bookmarks;
	private Long items;
	private Long subquestions;
}
