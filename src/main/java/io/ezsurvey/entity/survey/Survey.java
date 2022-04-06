package io.ezsurvey.entity.survey;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import io.ezsurvey.entity.BaseTime;
import io.ezsurvey.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서 Entity 관리시 필요
@Table(indexes = {@Index(name =  "survey_idx_member", columnList = "member_id")})
@Entity
@DynamicUpdate // 변경한 필드만 대응
public class Survey extends BaseTime {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	@Column(name = "survey_id")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY) // 여러 설문조사가 한 회원과 매핑될 수 있지만, 한 설문조사가 여러 회원과 매핑될 수 없음
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "survey_fk")) // FK 컬럼명과 제약 조건 지정
	private User user;
	
	@Column(length = 128, nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	private LocalDateTime distributed; // 배포 시작일 
	
	private LocalDateTime expires; // 배포 종료일

	@Column(nullable = false)
	@Convert(converter = StatusConverter.class)
	private Status status = Status.BEFORE;
	
	@Column(nullable = false)
	@Convert(converter = VisibilityConverter.class)
	private Visibility visibility = Visibility.HIDDEN; // 0=비공개, 1=링크 공개, 2=전체 공개; -1=삭제됨

	@Column(length = 36, unique = true)
	private String shared; // 설문조사 UUID값
	
	// 테이블에 없는 가상 컬럼
	@Basic(fetch = FetchType.LAZY) // 가상 컬럼이 필요한 경우에만 동적으로 서브쿼리 실행; 제대로 적용되는 것 같지 않음?
	@Formula("(select count(*) from bookmark_survey bs where bs.survey_id = survey_id)") // JPQL이 아니라 SQL; 서브쿼리이므로 () 필수
	private Long bookmarks;
	
	// 테이블에 없는 가상 컬럼
	@Basic(fetch = FetchType.LAZY)
	@Formula("(select count(*) from question q where q.survey_id = survey_id and q.parent_id is null)") // 자식 문항이 아닌 문항 수만 집계
	private Long questions;
	
	@Builder // 자동 생성되는 PK 값이나 등록일, 수정일을 builder()에서 제외해야 하기 때문에 클래스 수준 @Builder는 부적절
	public Survey(User user, String title, String content
			, Status status, Visibility visibility, String shared) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.status = status;
		this.visibility = visibility;
		this.shared = shared;
	}
	
	public Survey update(String title, String content, Visibility visibility, String shared) {
		this.title = title;
		this.content = content;
		this.visibility = visibility;
		this.shared = shared;
		
		return this;
	}
	
	public Survey updateDistributedAndExpires(LocalDateTime distributed, LocalDateTime expires) {
		this.distributed = distributed;
		this.expires = expires;
		
		return this;
	}
	
	public Survey setVisibilityToDeleted() {
		this.visibility = Visibility.DELETED;

		return this;
	}
	
	public Survey copy(User user, String title, String content) {
		this.id = null;
		this.user = user;
		this.title = title;
		this.content = content;
		this.distributed = null;
		this.expires = null;
		this.status = Status.BEFORE;
		this.visibility = Visibility.HIDDEN;
		this.shared = null;
		
		return this;
	}
}
