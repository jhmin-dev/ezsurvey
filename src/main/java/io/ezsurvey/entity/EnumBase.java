package io.ezsurvey.entity;

import java.util.Arrays;

public interface EnumBase { // View 레이어에서 사용하려는 enum에서 구현
	String getKey(); // View 레이어에서 서버로 전송될 값
	String getName(); // View 레이어에서 사용자에게 보여질 값
	
	static <E extends EnumBase> E findByKey(Class<E> enumClass, String key) { // enum 클래스와 문자열을 전달하면, enum 상수의 key 필드 값이 전달된 문자열과 일치하는 enum 상수를 반환
		if(key==null || key.isBlank()) return null;
		
		return Arrays.stream(enumClass.getEnumConstants())
				.filter(e -> e.getKey().equals(key))
				.findAny().orElseThrow(() -> new IllegalArgumentException());
	}
}
