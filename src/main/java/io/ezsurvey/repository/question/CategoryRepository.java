package io.ezsurvey.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.question.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
