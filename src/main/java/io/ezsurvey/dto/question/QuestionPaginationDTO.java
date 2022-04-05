package io.ezsurvey.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@Builder @AllArgsConstructor // @Builder는 인자가 있는 생성자를 요구
@NoArgsConstructor
public class QuestionPaginationDTO {
	private Long questionId;
	private String category;
	private Long parentId;
	private Boolean branched;
	private Boolean randomized;
	private Integer idx;
	private Integer subidx;
	private String varlabel;
	private String content;
	
	// 테이블에 없는 가상 컬럼
	private Long bookmarks;
	private Long items;
	private Long subquestions;
}
