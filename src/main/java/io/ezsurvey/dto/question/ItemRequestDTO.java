package io.ezsurvey.dto.question;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.util.HtmlUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class ItemRequestDTO {
	private Long itemId;
	private Long questionId;
	@NotNull
	private Integer value;
	@Size(max = 256)
	private String vallabel;
	@Size(max = 256)
	private String picture;
	
	// ServiceDTO to RequestDTO
	public ItemRequestDTO(ItemServiceDTO serviceDTO) {
		this.itemId = serviceDTO.getItemId();
		this.value = serviceDTO.getValue();
		this.vallabel = serviceDTO.getVallabel();
		this.picture = serviceDTO.getPicture();
	}
	
	// RequestDTO to ServiceDTO
	public ItemServiceDTO toServiceDTO() {
		return ItemServiceDTO.builder()
				.itemId(itemId)
				.value(value)
				.vallabel(HtmlUtils.htmlEscape(vallabel))
				.picture(picture)
				.build();
	}
}
