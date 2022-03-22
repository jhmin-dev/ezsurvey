package io.ezsurvey.entity.survey;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Visibility {
	PUBLIC("public", "전체 공개", (byte)2),
	LINK_ONLY("link_only", "일부 공개", (byte)1),
	HIDDEN("hidden", "비공개", (byte)0);
	
	public final String key;
	public final String name;
	public final Byte value;
	
	public static Visibility findByKey(String key) {
		return Arrays.stream(Visibility.values())
				.filter(visibility -> visibility.getKey().equals(key))
				.findFirst().orElseThrow(() -> new IllegalArgumentException());
	}

	public static Visibility findByValue(Byte value) {
		return Arrays.stream(Visibility.values())
				.filter(visibility -> visibility.getValue()==value)
				.findFirst().orElseThrow(() -> new IllegalArgumentException());		
	}
}
