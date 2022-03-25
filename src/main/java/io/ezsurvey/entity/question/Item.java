package io.ezsurvey.entity.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서 Entity 관리시 필요
@Entity
public class Item {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	@Column(name = "item_id")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "item_fk"))
	private Question question;
	
	@Column(nullable = false)
	private Integer value; // 응답 범주 값
	
	@Column(length = 256)
	private String vallabel; // 응답 범주 의미
	
	@Column(length = 256)
	private String picture; // 이미지 파일명
}
