package io.ezsurvey.repository.question;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.question.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	public Optional<Category> findByName(String name);
}
