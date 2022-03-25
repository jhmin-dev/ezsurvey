package io.ezsurvey.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class QuerydslConfig {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Bean
	public JPAQueryFactory jpaQueryFactory() { // Repository에서 사용할 Bean 등록
		return new JPAQueryFactory(entityManager);
	}
}
