package io.ezsurvey.repository;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

public class QuerydslUtil {
	// BookmarkSurvey에서 Survey를 검색하는 것처럼 JPAQuery에서 반환할 Entity와 검색 대상 Entity가 불일치할 수 있어 R과 E를 구분
	public static <R, E, Q extends EntityPathBase<E>> void applySort(JPAQuery<R> query, Q qclass, Pageable pageable) {
		// 쿼리에 조회를 시작할 rownum과 한 페이지에 보여질 레코드 수 지정
		query.offset(pageable.getOffset()).limit(pageable.getPageSize());
		
		// Spring Data JPA의 Sort를 Querydsl에서 사용 가능하도록 변환
		OrderSpecifier<?>[] orders = pageable.getSort().stream().map(order -> {
			Order direction = order.isAscending() ? Order.ASC : Order.DESC;
			PathBuilder<E> pathBuilder = new PathBuilder<E>(qclass.getType(), qclass.getMetadata());
			
			return new OrderSpecifier(direction, pathBuilder.get(order.getProperty()));
		}).toArray(OrderSpecifier[]::new);
		
		// 쿼리에 정렬 적용
		query.orderBy(orders);
	}
}
