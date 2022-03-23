package io.ezsurvey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Status;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.Provider;
import io.ezsurvey.repository.EnumMapper;

@Configuration
public class AppConfig {

	@Bean
	public EnumMapper enumMapper() {
		EnumMapper enumMapper = new EnumMapper();
		
		// View 레이어에서 사용할 EnumDTO를 factory에 등록
		enumMapper.put("Provider", Provider.class);
		enumMapper.put("SearchField", SearchField.class);
		enumMapper.put("Visibility", Visibility.class);
		enumMapper.put("Status", Status.class);
		
		return enumMapper;
	}
}
