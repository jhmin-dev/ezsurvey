package io.ezsurvey.repository.question;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static io.ezsurvey.entity.question.QItem.item;

import io.ezsurvey.dto.question.ItemServiceDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class CustomItemRepositoryImpl implements CustomItemRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ItemServiceDTO> findServiceDTOByQuestionId(Long questionId) {
		return jpaQueryFactory.select(Projections.fields(ItemServiceDTO.class
				, item.id.as("itemId") // 응답 범주 조회시에는 항상 문항 DTO를 별도 조회하기 때문에 question 필드 불필요
				, item.value, item.vallabel, item.picture))
				.from(item)
				.where(item.question.id.eq(questionId))
				.fetch();
	}
}
