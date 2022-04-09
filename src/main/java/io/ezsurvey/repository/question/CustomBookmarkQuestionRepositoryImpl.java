package io.ezsurvey.repository.question;

import static io.ezsurvey.entity.question.QBookmarkQuestion.bookmarkQuestion;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomBookmarkQuestionRepositoryImpl implements CustomBookmarkQuestionRepository {
	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public Long deleteByUserId(Long userId) {
		return jpaQueryFactory.delete(bookmarkQuestion)
				.where(bookmarkQuestion.user.id.eq(userId))
				.execute();
	}
}
