package io.ezsurvey.dto.question;

import io.ezsurvey.entity.question.Item;
import io.ezsurvey.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString(exclude = {"question"})
@Builder @AllArgsConstructor // @Builder는 인자가 있는 생성자를 요구
@NoArgsConstructor
public class ItemServiceDTO {
	private Long itemId;
	private Question question;
	private Integer value;
	private String vallabel;
	private String picture;
	
	// ServiceDTO to Entity
	public Item toEntity() {
		return Item.builder()
				.question(question)
				.value(value)
				.vallabel(vallabel)
				.picture(picture)
				.build();
	}
	
	public ItemServiceDTO updateQuestion(Question question) {
		this.question = question;
		
		return this;
	}
}
