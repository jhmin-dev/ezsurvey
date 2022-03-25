package io.ezsurvey.entity.question;

import javax.persistence.Column;
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

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "question_id"}))
@Entity
public class BookmarkQuestion {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	@Column(name = "bookmark_question_id")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "myquestion_fk_member"))
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "myquestion_fk_question"))
	private Question question;
}
