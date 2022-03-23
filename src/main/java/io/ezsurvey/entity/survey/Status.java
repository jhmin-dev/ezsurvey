package io.ezsurvey.entity.survey;

import io.ezsurvey.entity.EnumBaseValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status implements EnumBaseValue {
	BEFORE("before", "배포 전", (byte)0), // 수정 가능/응답 불가
	RUNNING("running", "배포 중", (byte)1), // 수정 불가/응답 가능
	AFTER("after", "배포 종료", (byte)2); // 수정 불가/응답 불가
	
	public final String key;
	public final String name;
	public final Byte value;
}
