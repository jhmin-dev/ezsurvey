package io.ezsurvey.dto.question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	
	public QuestionServiceDTO toServiceDTO() {
		return QuestionServiceDTO.builder()
				.category(EnumBase.findByKey(Category.class, category))
				.startFromOne(startFromOne)
				.branched(branched)
				.randomized(randomized)
				.varlabel(varlabel)
				.content(content)
				.article(article)
				.picture(picture)
				.build();
	}
}
