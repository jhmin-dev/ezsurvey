<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/css/list.css">
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
				<c:if test="${type eq 'question'}">
				<li>
					<select name="category">
						<option value="" <c:if test="${empty param.category}">selected</c:if>>전체</option>
						<c:forEach items="${category}" var="cat">
						<option value="${cat.key}" <c:if test="${cat.key==param.category}">selected</c:if>>${cat.name}</option>
						</c:forEach>
					</select>
				</li>
				</c:if>		
				<li>
					<input type="search" name="word" value="${param.word}">
				</li>
				<li>
					<label class="label-button">
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
			<li>
				<input type="button" value="새로고침" onclick="clearParameters();">
			</li>
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
			<c:if test="${type eq 'survey'}">
			<li class="lg">
				제목
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="title" data-direction="DESC">
					<i class="bi bi-sort-alpha-down-alt"></i>
				</label>
			</li>
			<li>
				문항
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="questions" data-direction="DESC">
					<i class="bi bi-sort-numeric-down-alt"></i>
				</label>
			</li>
			<li title="즐겨찾기 수">
				<i class="bi bi-bookmarks-fill"></i>
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="bookmarks" data-direction="DESC">
					<i class="bi bi-sort-numeric-down-alt"></i>
				</label>
			</li>
			<li>
				생성일
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="created" data-direction="DESC">
					<i class="bi bi-sort-numeric-down-alt"></i>
				</label>
			</li>
			</c:if>
			<c:if test="${type eq 'question'}">
			<li class="lg">
				변수명/문항
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="varlabel" data-direction="DESC">
					<i class="bi bi-sort-alpha-down-alt"></i>
				</label>
			</li>
			<li>
				유형
			</li>
			<li>
				응답 범주
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="items" data-direction="DESC">
					<i class="bi bi-sort-numeric-down-alt"></i>
				</label>
			</li>
			<li>
				하위 문항
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="subquestions" data-direction="DESC">
					<i class="bi bi-sort-numeric-down-alt"></i>
				</label>
			</li>
			</c:if>
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
				<label class="label-button">
					<input type="button" onclick="toggleSort(this);" data-field="user.name" data-direction="DESC">
					<i class="bi bi-sort-alpha-down-alt"></i>
				</label>
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
				<c:if test="${type eq 'survey'}">${element.surveyId}</c:if>
				<c:if test="${type eq 'question'}">${element.questionId}</c:if>
			</li>
			<li class="lg">
				<c:if test="${type eq 'survey'}">
				<a href="<c:if test="${link eq 'my'}">/edit</c:if>/project/${element.surveyId}">
					${element.title}
				</a>
				</c:if>
				<c:if test="${type eq 'question'}">
				<a href="/project/${element.surveyId}/preview/question/${element.questionId}">
					${element.varlabel}
				</a>
				</c:if>
			</li>
			<c:if test="${type eq 'survey'}">
			<li>
				${element.questions}
			</li>
			</c:if>
			<c:if test="${type eq 'question'}">
			<li data-category="${element.category}">
			
			</li>	
			</c:if>
			<li>
				<c:if test="${type eq 'survey'}">${element.bookmarks}</c:if>
				<c:if test="${type eq 'question'}">${element.items}</c:if>
			</li>
			<li>
				<c:if test="${type eq 'survey'}">
				<span class="date" data-created="${element.created}"></span>
				</c:if>
				<c:if test="${type eq 'question'}">${element.subquestions}</c:if>
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
			<li <c:if test="${element.userDeleted}">title="탈퇴한 계정"</c:if>>
				${element.userName}
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
				<div onclick="movePage(${startPageBlock-1});">
					이전
				</div>
			</li>
			<c:forEach begin="${startPageBlock}" end="${endPageBlock}" var="i">
			<li <c:if test="${(i==1 && empty param.page) || i==param.page}">class="active disabled"</c:if>>
				<div onclick="movePage(${i});">
					${i}
				</div>
			</li>
			</c:forEach>
			<li <c:if test="${endPageBlock==totalPages}">class="disabled"</c:if>>
				<div onclick="movePage(${endPageBlock+1});">
					다음
				</div>
			</li>
		</ul>
	</li>
	<!-- 페이지네이션 끝 -->
</ul>
</c:if>
<script type="text/javascript" src="/js/StringUtil.js"></script>
<script type="text/javascript" src="/js/ParamUtil.js"></script>
<script type="text/javascript" src="/js/ui/list.js"></script>
<script type="text/javascript" src="/js/ui/bookmark.js"></script>
<script type="text/javascript" src="/js/ajax/bookmark.js"></script>
</article>
</main>