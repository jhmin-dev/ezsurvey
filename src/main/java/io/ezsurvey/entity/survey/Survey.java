package io.ezsurvey.entity.survey;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import io.ezsurvey.entity.BaseTime;
import io.ezsurvey.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서 Entity 관리시 필요
@Entity
public class Survey extends BaseTime {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	private Long survey;
	
	@ManyToOne(optional = false) // 여러 설문조사가 한 회원과 매핑될 수 있지만, 한 설문조사가 여러 회원과 매핑될 수 없음
	@JoinColumn(name = "member", foreignKey = @ForeignKey(name = "survey_fk")) // FK 컬럼명과 제약 조건 지정
	private User user;
	
	@Column(length = 128, nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private boolean randomized = false;
	
	private LocalDateTime distributed; // 배포 시작일 
	
	private LocalDateTime expires; // 배포 종료일

	@Column(length = 1, nullable = false)
	private int status = 0; // 0=배포 전, 1=배포 중, 2=배포 후
	
	@Column(nullable = false)
	private boolean deleted = false;
	
	@Column(length = 1, nullable = false)
	private int visibility = 0; // 0=비공개, 1=링크 공개, 2=전체 공개

	@Column(length = 36, unique = true)
	private String shared; // 설문조사 UUID값
	
	@Builder // 자동 생성되는 PK 값이나 등록일, 수정일을 builder()에서 제외해야 하기 때문에 클래스 수준 @Builder는 부적절
	public Survey(User user, String title, String content
			, int status, int visibility, String shared) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.status = status;
		this.visibility = visibility;
		this.shared = shared;
	}
	
	public Survey update(String title, String content
			, int status, boolean deleted, int visibility, String shared) {
		this.title = title;
		this.content = content;
		this.status = status;
		this.visibility = visibility;
		this.shared = shared;
		
		return this;
	}
	
	public Survey updateDistributedAndExpires(LocalDateTime distributed, LocalDateTime expires) {
		this.distributed = distributed;
		this.expires = expires;
		
		return this;
	}
	
	public Survey updateDelete(boolean deleted) {
		this.deleted = deleted;

		return this;
	}
}
