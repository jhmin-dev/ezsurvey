<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	${title}
</div>
<article data-survey="${survey.survey}" data-link="${link}">
<div class="survey-title">
	${survey.title}
</div>
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
<ul>
	<li class="content">

	</li>
	<li class="item-container">

	</li>
</ul>
<ul class="question-menu">
	<li>
		<button type="button" class="point" onclick="makeQuestion();">추가하기</button>
	</li>
</ul>
</article>
<link rel="stylesheet" href="/css/preview.css">
<script type="text/javascript" src="/js/ui/question.js"></script>
<script type="text/javascript" src="/js/ajax/question.js"></script>
</main>