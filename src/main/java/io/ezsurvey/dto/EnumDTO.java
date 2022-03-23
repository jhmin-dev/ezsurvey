package io.ezsurvey.dto;

import io.ezsurvey.entity.EnumBase;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class EnumDTO {
	private String key;
	private String name;
	private Byte value;
	
	public EnumDTO(EnumBase enumBase) {
		this.key = enumBase.getKey();
		this.name = enumBase.getName();
	}
}
