package io.ezsurvey.repository.survey;

import static io.ezsurvey.entity.survey.QBookmarkSurvey.bookmarkSurvey;
import static io.ezsurvey.entity.survey.QSurvey.survey;
import static io.ezsurvey.entity.user.QUser.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.ezsurvey.dto.survey.SurveyPaginationDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.BookmarkSurvey;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.QuerydslUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomBookmarkSurveyRepositoryImpl implements CustomBookmarkSurveyRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<SurveyPaginationDTO> findPaginationDTOByVisibilityAndUser(User u, SearchField field, String word, Pageable pageable) {
		JPAQuery<SurveyPaginationDTO> content = jpaQueryFactory
				.select(Projections.fields(SurveyPaginationDTO.class
						, bookmarkSurvey.id.as("bookmarkId"), survey.id.as("surveyId")
						, survey.title, survey.created
						, survey.questions, survey.bookmarks)) // 즐겨찾기 목록에는 생성자 정보와 공개 범위 정보 모두 불필요
				.from(bookmarkSurvey)
				.innerJoin(bookmarkSurvey.survey, survey) // where절에서 survey의 필드로 검색하고 있기 때문에 innerJoin 명시하지 않으면 크로스 조인 발생
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, bookmarkSurvey.user.eq(u)
						, SurveySearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, bookmarkSurvey, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory.select(bookmarkSurvey.count())
				.from(bookmarkSurvey)
				.innerJoin(bookmarkSurvey.survey, survey) // where절에서 survey의 필드로 검색하고 있기 때문에 innerJoin 명시하지 않으면 크로스 조인 발생
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, bookmarkSurvey.user.eq(u)
						, SurveySearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetchOne());
	}

	@Override
	public BookmarkSurvey getBySurveyIdAndUserId(Long surveyId, Long userId) {
		JPAQuery<BookmarkSurvey> content = jpaQueryFactory.select(bookmarkSurvey)
				.from(bookmarkSurvey)
				.where(bookmarkSurvey.survey.id.eq(surveyId), bookmarkSurvey.user.id.eq(userId));
		
		return content.fetchOne();
	}

	@Override
	public Long deleteByIdIn(List<Long> bookmarkIds) {
		return jpaQueryFactory.delete(bookmarkSurvey)
				.where(bookmarkSurvey.id.in(bookmarkIds))
				.execute();
	}
	
	@Override
	public Long deleteByUserId(Long userId) {
		return jpaQueryFactory.delete(bookmarkSurvey)
				.where(bookmarkSurvey.user.id.eq(userId))
				.execute();
	}
}
