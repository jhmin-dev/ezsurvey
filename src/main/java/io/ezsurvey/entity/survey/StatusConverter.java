package io.ezsurvey.entity.survey;

import javax.persistence.AttributeConverter;

import io.ezsurvey.entity.EnumBaseValue;

public class StatusConverter implements AttributeConverter<Status, Byte> { // Entity 필드와 DB 값 사이의 변환
	@Override
	public Byte convertToDatabaseColumn(Status attribute) {
		return attribute.getValue();
	}

	@Override
	public Status convertToEntityAttribute(Byte dbData) {
		return EnumBaseValue.findByValue(Status.class, dbData);
	}
}
