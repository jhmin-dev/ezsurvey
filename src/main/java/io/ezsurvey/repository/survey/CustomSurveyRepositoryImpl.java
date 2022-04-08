package io.ezsurvey.repository.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static io.ezsurvey.entity.survey.QSurvey.survey;
import static io.ezsurvey.entity.user.QUser.user;

import io.ezsurvey.dto.survey.SurveyAuthDTO;
import io.ezsurvey.dto.survey.SurveyIndexDTO;
import io.ezsurvey.dto.survey.SurveyPaginationDTO;
import io.ezsurvey.dto.survey.SurveyServiceDTO;
import io.ezsurvey.entity.SearchField;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.entity.user.User;
import io.ezsurvey.repository.QuerydslUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomSurveyRepositoryImpl implements CustomSurveyRepository {
	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public Page<SurveyPaginationDTO> findByVisibility(SearchField field, String word, Pageable pageable) {
		JPAQuery<SurveyPaginationDTO> content = jpaQueryFactory
				.select(Projections.fields(SurveyPaginationDTO.class
						, survey.id.as("surveyId")
						, user.name.as("userName"), user.profileURL.as("userProfileURL"), user.deleted.as("userDeleted")
						, survey.title, survey.created
						, survey.bookmarks, survey.questions)) // 둘러보기 목록에는 생성자 정보 필요, 공개 범위 정보 불필요
				.from(survey)
				.innerJoin(survey.user, user)
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, SurveySearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, survey, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory.select(survey.count())
				.from(survey)
				.where(survey.visibility.eq(Visibility.PUBLIC)
						, SurveySearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetch().get(0));
	}

	@Override
	public Page<SurveyPaginationDTO> findByUser(User u, SearchField field, String word, Pageable pageable) {
		JPAQuery<SurveyPaginationDTO> content = jpaQueryFactory
				.select(Projections.fields(SurveyPaginationDTO.class
						, survey.id.as("surveyId")
						, survey.title, survey.created
						, survey.visibility
						, survey.bookmarks, survey.questions)) // 내 설문조사 목록에는 생성자 정보 불필요, 공개 범위 정보 필요
				.from(survey)
				.where(survey.visibility.ne(Visibility.DELETED)
						, survey.user.eq(u)
						, SurveySearchCondition.contains(field, word));
		
		// 페이징과 정렬 처리
		QuerydslUtil.applySort(content, survey, pageable);
		
		JPAQuery<Long> count = jpaQueryFactory.select(survey.count())
				.from(survey)
				.where(survey.visibility.ne(Visibility.DELETED)
						, survey.user.eq(u)
						, SurveySearchCondition.contains(field, word));
		
		// 총 레코드 수가 페이지 크기보다 작거나, 마지막 페이지인 경우 마지막 인자의 함수(쿼리)를 실행하지 않음
		return PageableExecutionUtils.getPage(content.fetch(), pageable
				, () -> count.fetch().get(0));
	}

	@Override
	public SurveyServiceDTO getServiceDTOById(Long surveyId) {
		return jpaQueryFactory
				.select(Projections.fields(SurveyServiceDTO.class
						, survey.id.as("surveyId"), survey.user
						, survey.title, survey.content
						, survey.created, survey.modified
						, survey.distributed, survey.expires, survey.status
						, survey.visibility, survey.shared
						, survey.bookmarks, survey.questions))
				.from(survey)
				.where(survey.id.eq(surveyId))
				.fetchOne();
	}
	
	@Override
	public SurveyIndexDTO getIndexDTOById(Long surveyId) {
		return jpaQueryFactory.select(Projections.fields(SurveyIndexDTO.class
				, survey.id.as("surveyId"), survey.title, survey.questions))
				.from(survey)
				.where(survey.id.eq(surveyId))
				.fetchOne();
	}
	
	@Override
	public SurveyAuthDTO getAuthDTOById(Long surveyId) {
		return jpaQueryFactory
				.select(Projections.fields(SurveyAuthDTO.class
						, survey.id.as("surveyId"), survey.user.id.as("userId")
						, survey.distributed, survey.expires, survey.status
						, survey.visibility, survey.shared))
				.from(survey)
				.where(survey.id.eq(surveyId))
				.fetchOne();
	}

	@Override
	public Long getUserIdById(Long surveyId) {
		return jpaQueryFactory.select(survey.user.id)
				.from(survey)
				.where(survey.id.eq(surveyId))
				.fetchOne();
	}
}
