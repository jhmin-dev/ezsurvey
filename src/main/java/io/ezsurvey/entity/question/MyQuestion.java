package io.ezsurvey.entity.question;

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

@Table(name ="myquestion", uniqueConstraints = @UniqueConstraint(columnNames = {"member", "question"}))
@Entity
public class MyQuestion {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	private Long myquestion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member", foreignKey = @ForeignKey(name = "myquestion_fk_member"))
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "question", foreignKey = @ForeignKey(name = "myquestion_fk_question"))
	private Question question;
}
