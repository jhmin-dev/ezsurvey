package io.ezsurvey.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.ezsurvey.entity.question.Category;
import io.ezsurvey.repository.question.CategoryRepository;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		categoryRepository.save(new Category("서답형"));
		categoryRepository.save(new Category("선다형"));
		categoryRepository.save(new Category("척도형"));
	}

}
