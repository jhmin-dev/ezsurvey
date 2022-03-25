package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.ezsurvey.entity.survey.Survey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;

public interface SurveyRepository extends JpaRepository<Survey, Long>{
	static final String BY_VISIBILITY = "SELECT s FROM Survey s WHERE s.visibility = io.ezsurvey.entity.survey.Visibility.PUBLIC";
	static final String BY_USER = "SELECT s FROM Survey s WHERE s.user = :user AND s.visibility != io.ezsurvey.entity.survey.Visibility.DELETED";
	static final String TITLE = "s.title LIKE %:word%";
	static final String CONTENT = "s.content LIKE %:word%";
	
	// 둘러보기
	Page<Survey> findByVisibility(Visibility visibility, Pageable pageable);
	Page<Survey> findByVisibilityAndTitleContaining(Visibility visibility
			, String title, Pageable pageable);
	Page<Survey> findByVisibilityAndContentContaining(Visibility visibility
			, String content, Pageable pageable);
	@Query(BY_VISIBILITY + " AND (" + TITLE + " OR " + CONTENT + ")")
	Page<Survey> findByTitleOrContent(@Param("word") String word, Pageable pageable);
	
	// 내 설문조사
	@Query(BY_USER)
	Page<Survey> findByUser(@Param("user") User user, Pageable pageable);
	@Query(BY_USER + " AND " + TITLE)
	Page<Survey> findByUserAndTitle(@Param("user") User user, @Param("word") String word, Pageable pageable);
	@Query(BY_USER + " AND " + CONTENT)
	Page<Survey> findByUserAndContent(@Param("user") User user, @Param("word") String word, Pageable pageable);
	@Query(BY_USER + " AND (" + TITLE + " OR " + CONTENT + ")")
	Page<Survey> findByUserAndTitleOrContent(@Param("user") User user, @Param("word") String word, Pageable pageable);
}
