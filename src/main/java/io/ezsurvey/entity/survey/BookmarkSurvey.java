package io.ezsurvey.entity.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.ezsurvey.entity.user.User;
import lombok.Builder;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "survey_id"}))
@Entity
public class BookmarkSurvey {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	@Column(name = "bookmark_survey_id")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "bookmark_survey_fk_member"))
	private User user;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id", foreignKey = @ForeignKey(name = "bookmark_survey_fk_survey"))
	private Survey survey;
	
	@Builder
	public BookmarkSurvey(User user, Survey survey) {
		this.user = user;
		this.survey = survey;
	}
}
