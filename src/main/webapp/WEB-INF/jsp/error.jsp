<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
<c:if test="${!empty errorCode}">
${errorCode.status}
</c:if>
${status}
</div>
<article>
<ul>
	<li>
		<div>${url}</div>
	</li>
	<li>
		<div>${exception.message}</div>
	</li>
	<c:if test="${errorCode.status eq 403}">
	<li>
		<c:if test="${empty user}">
		<button type="button" onclick="location.href = '/login';">로그인</button>
		</c:if>
		<c:if test="${!empty user}">
		<button type="button" onclick="location.href = '/logout';">로그아웃</button>
		</c:if>
	</li>
	</c:if>
	<c:if test="${empty errorCode}">
	<li>
		<label>콜 스택</label>
		<ul>
		<c:forEach items="${exception.stackTrace}" var="ste">
			<li>${ste}</li> 
		</c:forEach>
		</ul>
	</li>
	</c:if>
</ul>
</article>
</main>