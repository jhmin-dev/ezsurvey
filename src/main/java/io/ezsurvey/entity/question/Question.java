package io.ezsurvey.entity.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서 Entity 관리시 필요
@Entity
public class Question {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	@Column(name = "question_id")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "survey_id", foreignKey = @ForeignKey(name = "question_fk_survey"))
	private Survey survey;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "question_fk_member"))
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "question_fk_category"))
	private Category category;
	
	private Boolean startpositive; // 응답 범주 시작값; 선다형=1, 감정온도계형=0, 척도형 선택 가능

	@ManyToOne(optional = true)
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "question_fk_parent"))
	Question parent;
	
	private Boolean branched; // 하위 문항의 분기 여부; 0=분기 없음, 1=분기 있음
	
	private Boolean randomized; // 실험 문항 여부; 0=일반, 1=실험
	
	private Integer idx; // 설문조사 내 문항 번호
	
	private Integer subidx; // 문항 내 하위 문항 번호

	@Column(length = 32)
	private String variable; // 변수명; idx, subidx를 조합하여 자동 생성
	
	@Column(length = 256)
	private String varlabel; // 변수 의미; 생략시 idx, subidx, content를 조합하여 자동 생성
	
	@Lob
	@Column(nullable = false)
	private String content; // 질문 내용
	
	@Lob
	private String article; // 제시문 또는 안내문
	
	@Column(length = 256)
	private String picture; // 이미지 파일명
	
	@Builder
	public Question(Survey survey, User user, Category category, Boolean startpositive
			, Question parent, Boolean branched, Boolean randomized
			, String content, String article, String picture) {
		this.survey = survey;
		this.user = user;
		this.category = category;
		this.startpositive = startpositive;
		this.parent = parent;
		this.branched = branched;
		this.randomized = randomized;
		this.content = content;
		this.article = article;
		this.picture = picture;	
	}
	
	public Question update(Category category, Boolean startpositive
			, String content, String article, String picture) {
		this.category = category;
		this.startpositive = startpositive;
		this.content = content;
		this.article = article;
		this.picture = picture;
		
		return this;
	}
	
	public Question updateIdxAndSubidx(Integer idx, Integer subidx) {
		this.idx = idx;
		this.subidx = subidx;
		
		return this;
	}
	
	public Question updateVariableAndVarlabel(String variable, String varlabel) {
		this.variable = variable;
		this.varlabel = varlabel;
		
		return this;
	}
}