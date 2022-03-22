package io.ezsurvey.entity.question;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.ezsurvey.entity.survey.Survey;

@Entity
public class Template {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	private Long template;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "survey", foreignKey = @ForeignKey(name = "template_fk_survey"))
	private Survey survey;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "question", unique = true, foreignKey = @ForeignKey(name = "template_fk_question"))
	private Question question;
}
