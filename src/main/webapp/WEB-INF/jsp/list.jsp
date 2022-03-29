<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<main>
<div class="title">
	${title}
</div>
<article data-type="${type}" data-link="${link}">
<ul class="list-top">
	<li>
		<!-- 검색 시작 -->
		<form id="search">
			<ul>
				<li>
					<select name="field">
						<c:forEach items="${searchField}" var="sf">
						<option value="${sf.key}" <c:if test="${sf.key==param.field}">selected</c:if>>${sf.name}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<input type="text" name="word">
				</li>
				<li>
					<label>
						<i class="bi bi-search button"></i>
						<input type="submit">
					</label>
				</li>
			</ul>
		</form>
		<!-- 검색 끝 -->
	</li>
	<li class="page-menu">
		<ul>
			<c:if test="${type eq 'survey' && link ne 'bookmark'}">
			<li>
				<input type="button" class="point-button" value="설문조사 생성하기" onclick="location.href='/make/project';">
			</li>
			</c:if>
			<c:if test="${link eq 'bookmark'}">
			<li>
				<input type="button" value="선택 항목 삭제하기" onclick="deleteBookmarks();">
			</li>
			</c:if>
		</ul>
	</li>
</ul>
<div class="list-total notice">
	[${totalElements}개의 ${type eq 'survey' ? '설문조사' : '문항'}]
</div>
<c:if test="${totalElements==0}">
<div class="list-none notice">
	조회할 내용이 없습니다.
</div>
</c:if>
<c:if test="${totalElements>0}">
<ul class="list-main">
	<!-- 목록 헤더 시작 -->
	<li class="page-label">
		<ul class="${type}">
			<li class="sm">
				번호
			</li>
			<li class="lg">
				<c:if test="${type eq 'survey'}">제목</c:if>
				<c:if test="${type eq 'question'}">문항</c:if>
			</li>
			<li>
				<c:if test="${type eq 'survey'}">문항 수</c:if>
				
				<c:if test="${type eq 'question'}">유형</c:if>
			</li>
			<li title="즐겨찾기 수">
				<c:if test="${type eq 'survey'}">
				<i class="bi bi-bookmarks-fill"></i>
				</c:if>
				<c:if test="${type eq 'question'}">응답 범주 수</c:if>
			</li>
			<li>
				<c:if test="${type eq 'survey'}">생성일</c:if>
				<c:if test="${type eq 'question'}">하위 문항 수</c:if>
			</li>
			<c:if test="${link eq 'bookmark'}">
			<li>
				<label class="label-button">
					<input type="button" onclick="deleteBookmarks();">
					<i class="bi bi-trash3-fill"></i>
				</label>
			</li>
			</c:if>
			<c:if test="${link eq 'my'}">
			<li title="공개 범위">
				<i class="bi bi-door-open-fill"></i>
			</li>
			</c:if>
			<c:if test="${link eq 'list'}">
			<li>
				생성자
			</li>
			</c:if>
			<c:if test="${link eq 'index'}">
			<li>
				<i class="bi bi-arrow-down-up"></i>
			</li>
			</c:if>
		</ul>
	</li>
	<!-- 목록 헤더 끝 -->
	<!-- 목록 시작 -->
	<li class="page">
		<c:forEach items="${page.content}" var="element">
		<ul class="page-element ${type}">
			<li class="sm">
				<c:if test="${type eq 'survey'}">${element.survey}</c:if>
				<c:if test="${type eq 'question'}">${element.question}</c:if>
			</li>
			<li class="lg">
				<c:if test="${type eq 'survey'}">
				<c:if test="${link eq 'my'}">
				<a href="/edit/project/${element.survey}">
				</c:if>
				<c:if test="${link ne 'my'}">
				<a href="/project/${element.survey}">
				</c:if>
					${element.title}
				</a>
				</c:if>
				<c:if test="${type eq 'question'}">${element.content}</c:if>
			</li>
			<li>
				<c:if test="${type eq 'survey'}">${element.questions}</c:if>
				<c:if test="${type eq 'question'}">
				${element.category}
				</c:if>
			</li>
			<li>
				<c:if test="${type eq 'survey'}">${element.bookmarks}</c:if>
				<c:if test="${type eq 'question'}">응답 범주 수</c:if>
			</li>
			<li>
				<c:if test="${type eq 'survey'}">
				<span class="date" data-created="${element.created}"></span>
				</c:if>
				<c:if test="${type eq 'question'}">하위 문항 수</c:if>
			</li>
			<c:if test="${link eq 'bookmark'}">
			<li class="label-block">
				<label>
					<input type="checkbox" class="bookmark" data-bookmark="${element.bookmarkId}">
				</label>
			</li>
			</c:if>
			<c:if test="${link eq 'my'}">
			<li>
				<i class="visibility bi" data-visibility="${element.visibility}"></i>
			</li>
			</c:if>
			<c:if test="${link eq 'list'}">
			<li>
				${element.userName}
			</li>
			</c:if>
			<c:if test="${link eq 'index'}">
			<li>
				드래그
			</li>
			</c:if>
		</ul>
		</c:forEach>
	</li>
	<!-- 목록 끝 -->
	<!-- 페이지네이션 시작 -->
	<li class="pagination">
		<ul>
			<li <c:if test="${startPageBlock==1}">class="disabled"</c:if>>
				<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${startPageBlock-1}&field=${param.field}&word=${param.word}">
					이전
				</a>
			</li>
			<c:forEach begin="${startPageBlock}" end="${endPageBlock}" var="i">
			<li <c:if test="${(i==1 && empty param.page) || i==param.page}">class="active disabled"</c:if>>
				<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${i}&field=${param.field}&word=${param.word}">
					${i}
				</a>
			</li>
			</c:forEach>
			<li <c:if test="${endPageBlock==totalPages}">class="disabled"</c:if>>
				<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${endPageBlock+1}&field=${param.field}&word=${param.word}">
					다음 ${pageNumber}
				</a>
			</li>
		</ul>
	</li>
	<!-- 페이지네이션 끝 -->
</ul>
</c:if>
<link rel="stylesheet" href="/css/list.css">
<script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/js/StringUtil.js"></script>
<script type="text/javascript" src="/js/list.js"></script>
</article>
</main>