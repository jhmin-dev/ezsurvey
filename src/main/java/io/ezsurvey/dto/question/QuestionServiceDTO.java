package io.ezsurvey.dto.question;

import io.ezsurvey.entity.question.Category;
import io.ezsurvey.entity.question.Question;
import io.ezsurvey.entity.survey.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"survey", "parent"})
@Builder @AllArgsConstructor // @Builder는 인자가 있는 생성자를 요구
@NoArgsConstructor
public class QuestionServiceDTO {
	private Long questionId;
	private Survey survey;
	private Category category;
	private Boolean startFromOne;
	private Question parent;
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
	
	// ServiceDTO to Entity
	public Question toEntity() {
		return Question.builder()
				.survey(survey)
				.category(category)
				.startFromOne(startFromOne)
				.parent(parent)
				.branched(branched)
				.randomized(randomized)
				.idx(idx)
				.subidx(subidx)
				.varlabel(varlabel)
				.content(content)
				.article(article)
				.picture(picture)
				.build();
	}
	
	public QuestionServiceDTO updateParent(Question parent) {
		this.parent = parent;
		
		return this;
	}
}
