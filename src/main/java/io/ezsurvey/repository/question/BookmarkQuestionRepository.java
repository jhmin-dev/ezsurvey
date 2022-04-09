package io.ezsurvey.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.question.BookmarkQuestion;

public interface BookmarkQuestionRepository extends JpaRepository<BookmarkQuestion, Long>, CustomBookmarkQuestionRepository {

}
