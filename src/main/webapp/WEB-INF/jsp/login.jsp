<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/css/login.css">
<main>
<div class="title">
	로그인/회원 가입
</div>
<article>
	<c:if test="${!empty error}">
	<div class="login-error">
		<c:if test="${empty error.description}">
		<div>로그인/회원 가입 과정에서 오류가 발생하였습니다!</div>
		</c:if>
		<c:if test="${!empty error.description}">
		<div>${error.description}</div>
		</c:if>
	</div>
	</c:if>
	<c:forEach items="${provider}" var="p">
	<div id="${p.key}" class="login-button">
		<img src="/images/logo_${p.key}.png">
		<div><span class="word">${p.name}</span><span class="post"></span> 로그인하기</div>
	</div>	
	</c:forEach>
	<div class="notice">
		<i class="bi bi-exclamation-triangle-fill"></i>
		<span>네이버 로그인은 현재 개발자 계정으로만 이용 가능합니다.</span>
	</div>
</article>
<script type="text/javascript" src="/js/StringUtil.js"></script>
<script type="text/javascript" src="/js/ui/login.js"></script>
</main>