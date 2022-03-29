package io.ezsurvey.entity.question;

import io.ezsurvey.entity.EnumBaseValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category implements EnumBaseValue {
	MULTIPLE_CHOICE("multiple_choice", "선다형", (byte)1),
	LIKERT_SCALE("likert_scale", "척도형", (byte)2),
	SHORT_ANSWER("short_answer", "단답형", (byte)3);
	
	public final String key;
	public final String name;
	public final Byte value;
}
