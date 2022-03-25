package io.ezsurvey.entity.survey;

import javax.persistence.Entity;
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

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member", "survey"}))
@Entity
public class BookmarkSurvey {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	private Long bookmark_survey;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member", foreignKey = @ForeignKey(name = "bookmark_survey_fk_member"))
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "survey", foreignKey = @ForeignKey(name = "bookmark_survey_fk_survey"))
	private Survey survey;
	
	@Builder
	public BookmarkSurvey(User user, Survey survey) {
		this.user = user;
		this.survey = survey;
	}
}
