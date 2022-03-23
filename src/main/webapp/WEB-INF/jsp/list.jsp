<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	${title}
</div>
<article>
<c:if test="${count==0}">
<div>조회할 내용이 없습니다.</div>
</c:if>
<c:if test="${count!=0}">
<ul>
	<c:forEach items="${page.content}" var="element">
	<c:if test="${type eq 'survey'}">
	<li>${element.survey}</li>
	</c:if>
	<c:if test="${type eq 'question'}">
	<li>${element.question}</li>
	</c:if>
	<li>${element.title}</li>
	<li>${element.userName}</li>
	<li>${element.userProfileURL}</li>
	<li id="testtime">${element.created}</li>
	</c:forEach>
</ul>
</c:if>
<script type="text/javascript">
	let time = document.querySelector('#testtime').textContent
	console.log(new Date(time))
</script>
</article>
</main>