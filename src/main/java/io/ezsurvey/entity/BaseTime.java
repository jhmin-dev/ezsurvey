package io.ezsurvey.entity;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass // JPA Entity가 현재 클래스를 상속받으면 현재 클래스의 필드들을 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // 현재 클래스에 Auditing 기능을 포함
public abstract class BaseTime {
	@CreatedDate // INSERT시 값을 자동으로 채움
	private LocalDateTime registered;
	
	@LastModifiedDate // UPDATE시 값을 자동으로 채움
	private LocalDateTime modified;
}
