package io.ezsurvey.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@Builder @AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
	private Long questionId;
	private Long surveyId;
	private String category;
	private Boolean startFromOne;
	private Long parentId;
	private Boolean branched;
	private Boolean randomized;
	private Integer idx;
	private Integer subidx;
	private String varlabel;
	private String content;
	private String article;
	private String picture;
	
	// 테이블에 없는 가상 컬럼
	private Long bookmarks;
	private Long items;
	private Long subquestions;
	
	// PaginationDTO to ResponseDTO
	public QuestionResponseDTO(QuestionPaginationDTO paginationDTO) {
		this.questionId = paginationDTO.getQuestionId();
		this.category = paginationDTO.getCategory().getKey();
		this.parentId = paginationDTO.getParentId();
		this.branched = paginationDTO.getBranched();
		this.randomized = paginationDTO.getRandomized();
		this.idx = paginationDTO.getIdx();
		this.subidx = paginationDTO.getSubidx();
		this.varlabel = paginationDTO.getVarlabel();
		this.content = paginationDTO.getContent();
		
		// 테이블에 없는 가상 컬럼
		this.bookmarks = paginationDTO.getBookmarks();
		this.items = paginationDTO.getItems();
		this.subquestions = paginationDTO.getSubquestions();
	}
}
