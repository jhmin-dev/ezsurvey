<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<main>
<div class="title">
	설문조사 생성
</div>
<article>
<form:form modelAttribute="surveyWebDTO" id="make">
	<form:errors cssClass="error notice" element="div"/>
	<form:errors path="visibility" cssClass="error notice" element="div"/>
	<form:hidden path="visibility" value="hidden" />
	<ul>
		<li>
			<form:label path="title">제목</form:label>
			<form:input path="title"/>
			<form:errors path="title" cssClass="error notice" element="div"/>
		</li>
		<li>
			<form:label path="content">내용</form:label>
			<form:textarea path="content"/>
			<form:errors path="content" cssClass="error notice" element="div"/>
		</li>
		<li class="toggle">
			<label>공개</label>
			<div class="toggle-container" id="public">
				<div class="toggle-switch"></div>
			</div>
			<div class="notice">
				설문조사를 전체 공개 또는 일부 공개
			</div>
		</li>
		<li class="toggle">
			<label>링크 생성</label>
			<div class="toggle-container" id="share">
				<div class="toggle-switch"></div>
			</div>
			<div class="notice">
				설문조사를 링크 받은 사람에게만 공개
			</div>
		</li>
		<li>
			<input type="submit" value="생성하기">
		</li>
	</ul>
</form:form>
</article>
<link rel="stylesheet" href="/css/make_edit.css">
<script type="text/javascript" src="/js/survey.js"></script>
</main>