<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	${title}
</div>
<article data-survey="${survey.surveyId}" data-link="${link}">
<ul class="question-info">
	<li class="survey-title">
		${survey.title}
	</li>
	<li class="post-result display-none">
		<div>
			<i class="bi"></i><span></span>
		</div>
	</li>
</ul>
<ul class="question-type">
	<li>
		<label>문항 유형</label>
		<select name="category">
			<c:forEach items="${category}" var="cat">
			<option value="${cat.key}">${cat.name}</option>
			</c:forEach>
		</select>
	</li>
	<li>
		<label>응답 범주 수</label>
		<input type="number" name="items" min="1" max="11">
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
		</c:if>
		<c:if test="${link eq 'edit'}">
		<button type="button" class="point-button" onclick="">수정하기</button>
		</c:if>
		<button type="button" onclick="history.go(-1);">이전으로</button>
	</li>
</ul>
</article>
<link rel="stylesheet" href="/css/question.css">
<script type="text/javascript" src="/js/UIUtil.js"></script>
<script type="text/javascript" src="/js/ui/question.js"></script>
<script type="text/javascript" src="/js/ajax/question.js"></script>
</main>