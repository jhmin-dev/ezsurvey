package io.ezsurvey.entity.question;

import javax.persistence.AttributeConverter;

import io.ezsurvey.entity.EnumBaseValue;

public class CategoryConverter implements AttributeConverter<Category, Byte> {
	@Override
	public Byte convertToDatabaseColumn(Category attribute) {
		return attribute.getValue();
	}

	@Override
	public Category convertToEntityAttribute(Byte dbData) {
		return EnumBaseValue.findByValue(Category.class, dbData);
	}
}
