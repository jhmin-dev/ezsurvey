package io.ezsurvey.repository.survey;

import static io.ezsurvey.entity.survey.QBookmarkSurvey.bookmarkSurvey;
import static io.ezsurvey.entity.survey.QSurvey.survey;
import static io.ezsurvey.entity.user.QUser.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.QuerydslUtil;

public class CustomBookmarkSurveyRepositoryImpl implements CustomBookmarkSurveyRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public CustomBookmarkSurveyRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}
	
	@Override
	public Page<BookmarkSurvey> findByVisibilityAndUser(User member, SearchField field, String word, Pageable pageable) {
		JPAQuery<BookmarkSurvey> content = jpaQueryFactory
				.select(bookmarkSurvey).from(bookmarkSurvey)
				.innerJoin(bookmarkSurvey.survey, survey).fetchJoin() // where절에서 survey의 필드로 검색하고 있기 때문에 innerJoin 명시하지 않으면 크로스 조인 발생
				.innerJoin(bookmarkSurvey.user, user).fetchJoin() // N+1 방지; select(bookmarkSurvey)이므로 bookmarkSurvey.user에 대해 fetchJoin해야 함
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, user.eq(member)
						, SurveySearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, bookmarkSurvey, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory.select(bookmarkSurvey.count()).from(bookmarkSurvey)
				.innerJoin(bookmarkSurvey.survey, survey) // where절에서 survey의 필드로 검색하고 있기 때문에 innerJoin 명시하지 않으면 크로스 조인 발생
				.innerJoin(bookmarkSurvey.user, user)
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, user.eq(member)
						, SurveySearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetch().get(0));
	}

	@Override
	public BookmarkSurvey getBySurveyAndUser(Long survey_id, Long member_id) {
		JPAQuery<BookmarkSurvey> content = jpaQueryFactory.select(bookmarkSurvey).from(bookmarkSurvey)
				.innerJoin(bookmarkSurvey.survey, survey)
				.innerJoin(bookmarkSurvey.user, user)
				.where(survey.id.eq(survey_id), user.id.eq(member_id));
		
		return content.fetchOne();
	}

	@Override
	public Long deleteBySurveyAndUser(Long survey_id, Long member_id) {
		return jpaQueryFactory.delete(bookmarkSurvey)
				.where(bookmarkSurvey.survey.id.eq(survey_id), bookmarkSurvey.user.id.eq(member_id)).execute();
	}

	@Override
	public Long deleteByIdIn(List<Long> bookmarks, Long member_id) {
		return jpaQueryFactory.delete(bookmarkSurvey)
				.where(bookmarkSurvey.id.in(bookmarks), bookmarkSurvey.user.id.eq(member_id)).execute();
	}
}
