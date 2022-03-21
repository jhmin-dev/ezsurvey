package io.ezsurvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Entity 생성 및 수정 시간 자동화
@SpringBootApplication
public class EZSurveyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EZSurveyApplication.class, args);
	}

}
