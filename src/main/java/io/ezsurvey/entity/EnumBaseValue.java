package io.ezsurvey.entity;

import java.util.Arrays;

public interface EnumBaseValue extends EnumBase { // DB에 Byte 값을 저장하려는 enum에서 구현
	Byte getValue(); // DB에 저장될 값
	
	static <E extends EnumBaseValue> E findByValue(Class<E> enumClass, Byte value) { // enum 클래스와 Byte 값을 전달하면, enum 상수의 value 필드 값이 전달된 값과 일치하는 enum 상수를 반환
		return Arrays.stream(enumClass.getEnumConstants())
				.filter(e -> e.getValue()==value)
				.findAny().orElseThrow(() -> new IllegalArgumentException());
	}
}
