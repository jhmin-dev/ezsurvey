package io.ezsurvey.entity.question;

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

import io.ezsurvey.entity.survey.Survey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서 Entity 관리시 필요
@Table(indexes = {@Index(name = "question_idx_survey", columnList = "survey_id")
				, @Index(name = "question_idx_parent", columnList = "parent_id")})
@Entity
@DynamicUpdate // 변경한 필드만 대응
public class Question {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	@Column(name = "question_id")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id", foreignKey = @ForeignKey(name = "question_fk_survey"))
	private Survey survey;
	
	@Column(nullable = false)
	@Convert(converter = CategoryConverter.class)
	private Category category; // 1=선다형, 2=척도형, 3=단답형
	
	private Boolean startFromOne; // 응답 범주 시작값; 선다형=1, 감정온도계형=0, 척도형 선택 가능

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "question_fk_parent"))
	Question parent;
	
	private Boolean branched; // 하위 문항의 분기 여부; 0=분기 없음, 1=분기 있음
	
	private Boolean randomized; // 실험 문항 여부; 0=일반, 1=실험
	
	private Integer idx; // 설문조사 내 문항 번호
	
	private Integer subidx; // 문항 내 하위 문항 번호

	@Column(length = 256)
	private String varlabel; // 변수 의미; 생략시 idx, subidx, content를 조합하여 자동 생성
	
	@Lob
	@Column(nullable = false)
	private String content; // 질문 내용
	
	@Lob
	private String article; // 제시문 또는 안내문
	
	@Column(length = 256)
	private String picture; // 이미지 파일명
	
	// 테이블에 없는 가상 컬럼
	@Basic(fetch = FetchType.LAZY) // 가상 컬럼이 필요한 경우에만 동적으로 서브쿼리 실행; 제대로 적용되는 것 같지 않음?
	@Formula("(select count(*) from bookmark_question bq where bq.question_id = question_id)") // JPQL이 아니라 SQL; 서브쿼리이므로 () 필수
	private Long bookmarks;
	
	// 테이블에 없는 가상 컬럼
	@Basic(fetch = FetchType.LAZY)
	@Formula("(select count(*) from item i where i.question_id = question_id)")
	private Long items;
	
	// 테이블에 없는 가상 컬럼
	@Basic(fetch = FetchType.LAZY)
	@Formula("(select count(*) from question q where q.parent_id = question_id)")
	private Long subquestions;
	
	@Builder
	public Question(Survey survey, Category category, Boolean startFromOne
			, Question parent, Boolean branched, Boolean randomized
			, String varlabel, String content, String article, String picture) {
		this.survey = survey;
		this.category = category;
		this.startFromOne = startFromOne;
		this.parent = parent;
		this.branched = branched;
		this.randomized = randomized;
		this.varlabel = varlabel;
		this.content = content;
		this.article = article;
		this.picture = picture;	
	}
	
	public Question update(String varlabel, String content, String article, String picture) {
		this.varlabel = varlabel;
		this.content = content;
		this.article = article;
		this.picture = picture;
		
		return this;
	}
	
	public Question setStartFromOne(Boolean startFromOne) {
		this.startFromOne = startFromOne;
		
		return this;
	}
	
	public Question updateIdxAndSubidx(Integer idx, Integer subidx) {
		this.idx = idx;
		this.subidx = subidx;
		
		return this;
	}
	
	public Question copy(Survey survey, Question parent) {
		this.id = null;
		this.survey = survey;
		if(this.parent!=null) this.parent = parent;
		
		return this;
	}
}
