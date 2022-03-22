<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	둘러보기
</div>
<article>
<ul>
	<c:forEach items="${list.content}" var="survey">
	<li>${survey.survey}</li>
	<li>${survey.title}</li>
	<li>${survey.created}</li>
	</c:forEach>
</ul>
</article>
</main>