package io.ezsurvey.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="member")
@Entity
public class User {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATED AS IDENTITY
	private Long member;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 32, nullable = false)
	private Provider provider;

	@Column(length = 254, nullable = false, unique = true)
	private String email;
	
	private Boolean emailVerified;
	
	@Column(length = 121, nullable = false)
	private String name;
	
	@Column(length = 256, nullable = false)
	private String profileURL;

	@Enumerated(EnumType.STRING)
	@Column(length = 32, nullable = false)
	private Role role;
	
	@Builder
	public User(Provider provider, String email, Boolean emailVerified
			, String name, String profileURL, Role role) {
		this.provider = provider;
		this.email = email;
		this.emailVerified = emailVerified;
		this.name = name;
		this.profileURL = profileURL;
		this.role = role;
	}
	
	public User update(Boolean emailVerified, String name, String profileURL) {
		this.emailVerified = emailVerified;
		this.name = name;
		this.profileURL = profileURL;
		
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
}
