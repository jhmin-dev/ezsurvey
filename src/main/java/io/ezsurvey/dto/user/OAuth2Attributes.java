package io.ezsurvey.dto.user;

import java.util.Map;

import io.ezsurvey.entity.user.Provider;
import io.ezsurvey.entity.user.Role;
import io.ezsurvey.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class OAuth2Attributes {
	private Map<String, Object> attributes;
	private String userNameAttribute;
	private Provider provider;
	private String email;
	private Boolean emailVerified;
	private String name;
	private String profileURL;
	
	
	@Builder
	public OAuth2Attributes(Map<String, Object> attributes
			, String userNameAttribute, Provider provider
			, String email, Boolean emailVerified, String name, String profileURL) {
		this.attributes = attributes;
		this.userNameAttribute = userNameAttribute;
		this.provider = provider;
		this.email = email;
		this.emailVerified = emailVerified;
		this.name = name;
		this.profileURL = profileURL;
	}
	
	public static OAuth2Attributes of(Provider provider
			, String userNameAttribute, Map<String, Object> attributes) {
		if(provider==Provider.KAKAO) {
			return ofKakao(userNameAttribute, attributes);
		}
		else if(provider==Provider.NAVER) {
			return ofNaver(userNameAttribute, attributes);
		}
		return ofGoogle(userNameAttribute, attributes);
	}
	
	private static OAuth2Attributes ofGoogle(String userNameAttribute, Map<String, Object> attributes) {
		return OAuth2Attributes.builder()
						.attributes(attributes)
						.userNameAttribute(userNameAttribute)
						.provider(Provider.GOOGLE)
						.email((String)attributes.get("email"))
						.name((String)attributes.get("name"))
						.profileURL((String)attributes.get("picture"))
						.build();
	}
	
	private static OAuth2Attributes ofKakao(String userNameAttribute, Map<String, Object> attributes) {
		Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
		
		return OAuth2Attributes.builder()
				.attributes(attributes)
				.userNameAttribute(userNameAttribute)
				.provider(Provider.KAKAO)
				.email((String)kakao_account.get("email"))
				.emailVerified((Boolean)kakao_account.get("is_email_verified"))
				.name((String)profile.get("nickname"))
				.profileURL((String)profile.get("profile_image_url"))
				.build();
	}
	
	private static OAuth2Attributes ofNaver(String userNameAttribute, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
		
		return OAuth2Attributes.builder()
				.attributes(attributes)
				.userNameAttribute(userNameAttribute)
				.provider(Provider.NAVER)
				.email((String)response.get("email"))
				.name((String)response.get("name"))
				.profileURL((String)response.get("profile_image"))
				.build();
	}
	
	public User toEntity() {
		return User.builder()
						.provider(provider)
						.email(email)
						.emailVerified(emailVerified)
						.name(name)
						.profileURL(profileURL)
						.role(Role.USER)
						.build();
	}
}
