package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.user.User;

public interface BookmarkSurveyRepository extends JpaRepository<BookmarkSurvey, Long> {
	static final String BY_USER = "SELECT s FROM BookmarkSurvey bs JOIN bs.survey s WHERE bs.user = :user AND s.visibility = io.ezsurvey.entity.survey.Visibility.PUBLIC";
	static final String TITLE = "s.title LIKE %:word%";
	static final String CONTENT = "s.content LIKE %:word%";
	
	long countBySurvey(Survey survey);
	
	// 즐겨찾기: 설문조사
	@Query(BY_USER)
	Page<Survey> findSurveyByUser(@Param("user") User user, Pageable pageable);
	@Query(BY_USER + " AND " + TITLE)
	Page<Survey> findSurveyByUserAndTitle(@Param("user") User user, @Param("word") String word, Pageable pageable);
	@Query(BY_USER + " AND " + CONTENT)
	Page<Survey> findSurveyByUserAndContent(@Param("user") User user, @Param("word") String word, Pageable pageable);
	@Query(BY_USER + " AND (" + TITLE + " OR " + CONTENT + ")")
	Page<Survey> findSurveyByUserAndTitleOrContent(@Param("user") User user, @Param("word") String word, Pageable pageable);
}
