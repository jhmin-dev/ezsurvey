<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	${title}
</div>
<article data-survey="${survey}" data-link="${link}">
<div class="survey-title">
	설문조사 제목
</div>
<ul class="question-type">
	<li class="category">
	</li>
	<li class="items">
		<input type="number" min="0" max="11" value="4">
	</li>
</ul>
<ul>
	<li class="content">
		<form name="question">
			<input type="hidden" name="survey_id" value="${survey}">
			<input type="hidden" name="category" value="multiple_choice">
			<input type="hidden" name="startFromOne" value="true">
			<label for="content">문항</label>
			<input type="text" name="varlabel"/>
			<input type="text" name="content"/>
		</form>
	</li>
	<li class="item-container">
		<form name="item">
			<label for="value">번호</label>
			<input type="number" name="value" value="1" readonly>
			<label for="vallabel">보기</label>
			<input type="text" name="vallabel">
		</form>
		<form name="item">
			<label for="value">번호</label>
			<input type="number" name="value" value="2"  readonly>
			<label for="vallabel">보기</label>
			<input type="text" name="vallabel">
		</form>
		<form name="item">
			<label for="value">번호</label>
			<input type="number" name="value" value="3" readonly>
			<label for="vallabel">보기</label>
			<input type="text" name="vallabel">
		</form>
		<form name="item">
			<label for="value">번호</label>
			<input type="number" name="value" readonly>
			<label for="vallabel">보기</label>
			<input type="text" name="vallabel">
		</form>
	</li>
</ul>
<ul class="question-menu">
	<li>
		<button type="button" class="point" onclick="makeQuestion();">추가하기</button>
	</li>
</ul>
</article>
<link rel="stylesheet" href="/css/preview.css">
<script type="text/javascript" src="/js/ajax/question.js"></script>
</main>