<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/summernote-0.8.18-dist/summernote-lite.min.css">
<link rel="stylesheet" href="/css/question.css">
<main data-title="${title} · ${survey.title}">
<div class="title">
	<div>${title}</div>
	<div class="survey-title">
		《${survey.title}》
	</div>
</div>
<article data-survey="${survey.surveyId}" data-link="${link}">
<div class="post-result display-none">
	<div>
		<i class="bi"></i><span></span>
	</div>
</div>
<ul class="question-type">
	<li>
		<label>문항 유형</label>
		<select name="category" <c:if test="${link eq 'edit'}">disabled</c:if>>
			<c:forEach items="${category}" var="cat">
			<option value="${cat.key}">${cat.name}</option>
			</c:forEach>
		</select>
	</li>
	<li>
		<label>응답 범주 수</label>
		<input type="number" name="items" min="1" max="11" <c:if test="${link eq 'edit'}">disabled</c:if>>
	</li>
</ul>
<ul class="question-main">
	<li class="content">

	</li>
	<li class="item-container-label display-none">
		<label>
			응답 범주
		</label>
		<div class="notice">
			응답 범주의 값은 자동으로 부여됩니다.<br>
			응답 범주의 내용은 생략 가능하며, 최대 256자까지 입력 가능합니다.
		</div>
	</li>
	<li class="item-container">

	</li>
</ul>
<ul class="question-menu">
	<li>
		<c:if test="${link eq 'make'}">
		<button type="button" class="point-button" onclick="makeQuestion();">추가하기</button>
		<div class="notice">현재 작성 중인 문항은 추가하지 않으면 사라집니다.</div>
		</c:if>
		<c:if test="${link eq 'edit'}">
		<button type="button" class="point-button" onclick="editQuestion();">수정하기</button>
		</c:if>
	</li>
	<li>
		<button type="button" class="reverse-button" onclick="location.href = '/edit/project/${survey.surveyId}/index';">
			<c:if test="${link eq 'make'}">완료하기</c:if>
			<c:if test="${link eq 'edit'}">문항 관리</c:if>
		</button>
		<c:if test="${link eq 'edit'}">
		<button type="button" onclick="deleteQuestion()">삭제하기</button>
		</c:if>
		<button type="button" onclick="history.go(-1);">이전으로</button>
	</li>
</ul>
</article>
<div class="modal">
	<div class="modal-content grid plain">
		<div>
			<i class="bi bi-check"></i>
			<span></span>
		</div>
		<button type="submit" class="close-button">확인</button>
	</div>
</div>
<script type="text/javascript" src="/summernote-0.8.18-dist/summernote-lite.min.js"></script>
<script type="text/javascript" src="/summernote-0.8.18-dist/summernote-ko-KR.min.js"></script>
<script type="text/javascript" src="/js/UIUtil.js"></script>
<script type="text/javascript" src="/js/ui/question.js"></script>
<script type="text/javascript" src="/js/ajax/question.js"></script>
<script type="text/javascript" src="/js/onload/question.js"></script>
<script type="text/javascript" src="/js/onload/summernote.js"></script>
</main>