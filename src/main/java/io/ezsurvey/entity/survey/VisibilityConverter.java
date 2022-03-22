package io.ezsurvey.entity.survey;

import javax.persistence.AttributeConverter;

public class VisibilityConverter implements AttributeConverter<Visibility, Byte> {
	@Override
	public Byte convertToDatabaseColumn(Visibility attribute) {
		return attribute==null ? null : attribute.getValue();
	}

	@Override
	public Visibility convertToEntityAttribute(Byte dbData) {
		return Visibility.findByValue(dbData);
	}
}
