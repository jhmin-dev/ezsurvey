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
	@Column(name = "member_id")
	private Long id;
	
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
	
	@Column(nullable = false)
	private boolean deleted = false;
	
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
	
	public User delete() {
		this.email = this.id.toString(); // 재가입시 새 PK를 부여하기 위해 이메일 필드 값을 제거; 이메일은 Not Null에 UK이므로 빈 문자열 대신 기존 PK를 저장
		this.emailVerified = null;
		this.name = DeletedAccount.NAME.getName();
		this.profileURL = DeletedAccount.PROFILE_URL.getName();
		this.role = Role.GUEST; // 탈퇴 계정은 권한을 비회원으로 변경
		this.deleted = true;
		
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
}
