package io.ezsurvey.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.question.Category;
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
		enumMapper.put("Visibility", Visibility.class);
		enumMapper.put("Status", Status.class);
		enumMapper.put("Category", Category.class);
		
		return enumMapper;
	}
	
	@Bean
	public List<EnumDTO> searchFieldSurvey() { // View 레이어에서 설문조사 검색 필드로 사용할 리스트를 등록
		return Arrays.stream(SearchField.values())
				.filter(sf -> !sf.getKey().startsWith("q"))
				.map(EnumDTO::new).collect(Collectors.toList());
	}
	
	@Bean
	public List<EnumDTO> searchFieldQuestion() { // View 레이어에서 문항 검색 필드로 사용할 리스트를 등록
		return Arrays.stream(SearchField.values())
				.filter(sf -> sf.getKey().startsWith("q"))
				.map(EnumDTO::new).collect(Collectors.toList());
	}
}
