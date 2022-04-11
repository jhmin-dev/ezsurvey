package io.ezsurvey.dto.question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.util.HtmlUtils;

import io.ezsurvey.entity.EnumBase;
import io.ezsurvey.entity.question.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class QuestionRequestDTO {
	private Long questionId;
	private String category;
	private Boolean startFromOne;
	private Long parentId;
	private Boolean branched;
	private Boolean randomized;
	@Size(max = 256)
	private String varlabel;
	@NotBlank
	private String content;
	private String article;
	@Size(max = 256)
	private String picture;
	
	// ServiceDTO to RequestDTO
	public QuestionRequestDTO(QuestionServiceDTO serviceDTO) {
		this.questionId = serviceDTO.getQuestionId();
		this.category = serviceDTO.getCategory().getKey();
		this.startFromOne = serviceDTO.getStartFromOne();
		this.branched = serviceDTO.getBranched();
		this.randomized = serviceDTO.getRandomized();
		this.varlabel = serviceDTO.getVarlabel();
		this.content = serviceDTO.getContent();
		this.picture = serviceDTO.getPicture();
	}
	
	// RequestDTO to ServiceDTO
	public QuestionServiceDTO toServiceDTO() {
		return QuestionServiceDTO.builder()
				.questionId(questionId)
				.category(EnumBase.findByKey(Category.class, category))
				.startFromOne(startFromOne)
				.branched(branched)
				.randomized(randomized)
				.varlabel(HtmlUtils.htmlEscape(varlabel))
				.content(content)
				.article(article)
				.picture(picture)
				.build();
	}
}
