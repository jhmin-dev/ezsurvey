<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/css/exception.css">
<main data-title="오류가 발생했습니다!">
<div class="title">
<c:choose>
<c:when test="${!empty errorCode}">
${errorCode.status} ${errorCode}
</c:when>
<c:when test="${!empty status}">
${status} ${error}
</c:when>
<c:otherwise>
ERROR
</c:otherwise>
</c:choose>
</div>
<article>
<ul class="exception">
	<li class="alert">
		<div>
			<i class="bi bi-exclamation-triangle-fill"></i><span>오류가 발생했습니다!</span>
		</div>
	</li>
	<!-- 오류 메시지 시작 -->
	<li class="message notice">
		<div>
			원인: 
			<c:choose>
			<c:when test="${!empty errorCode || !empty exception}">
			${exception.message}
			</c:when>
			<c:otherwise>
			<c:if test="${status eq 403}">
			접근 권한이 없습니다.
			</c:if>
			<c:if test="${status eq 404}">
			요청한 자원이 서버에 존재하지 않습니다.
			</c:if>
			<c:if test="${status eq 500}">
			서버 내부 오류입니다.
			</c:if>
			</c:otherwise>
			</c:choose>
		</div>
	</li>
	<!-- 오류 메시지 끝 -->
	<!-- 이동 버튼 시작 -->
	<li class="redirect">
		<c:choose>
		<c:when test="${(!empty errorCode && errorCode.status eq 403) || (empty errorCode && status eq 403)}">
		<c:if test="${empty user}">
		<button type="button" class="reverse-button" onclick="location.href = '/login';">로그인</button>
		</c:if>
		<c:if test="${!empty user}">
		<button type="button" class="reverse-button" onclick="location.href = '/logout';">로그아웃</button>
		</c:if>
		</c:when>
		<c:otherwise>	
		<button type="button" class="reverse-button" onclick="location.href = '/';">메인으로</button>
		</c:otherwise>
		</c:choose>
		<button type="button" onclick="history.go(-1);">이전으로</button>
	</li>
	<!-- 이동 버튼 끝 -->
</ul>
</article>
</main>