package io.ezsurvey.entity.survey;

import javax.persistence.AttributeConverter;

import io.ezsurvey.entity.EnumBaseValue;

public class VisibilityConverter implements AttributeConverter<Visibility, Byte> { // Entity 필드와 DB 값 사이의 변환
	@Override
	public Byte convertToDatabaseColumn(Visibility attribute) {
		return attribute.getValue();
	}

	@Override
	public Visibility convertToEntityAttribute(Byte dbData) {
		return EnumBaseValue.findByValue(Visibility.class, dbData);
	}
}
