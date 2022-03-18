<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
<div class="logo">logo</div>
<div class="title">ezsurvey</div>
<nav class="main-menu">
	<ul>
		<c:if test="${empty user}">
		<li>
			<a href="/login">
				로그인/회원 가입
			</a>
		</li>
		</c:if>
		<c:if test="${!empty user}">
		<li>
			<a href="/logout">
				로그아웃
			</a>
		</li>
		</c:if>
		<li>
			<a href="/project">
				둘러보기
			</a>
		</li>
		<li>
			<a href="my/project">
				내 설문조사
			</a>
		</li>
		<c:if test="${!empty user}">
		<li>
			<a href="/make/project">
				<i class="bi bi-plus"></i>
			</a>
		</li>
		<li>
			<a>
				<img class="profile" src="${user.profileURL}">
			</a>
		</li>
		</c:if>	
	</ul>
</nav>
<c:if test="${!empty user}">
<nav class="dropdown-menu">
	<div class="tail"><i class="bi bi-triangle-fill"></i></div>
	<ul>
		<li>
			${user.name}님으로 로그인됨
		</li>
		<li>
			내 정보 보기
		</li>
		<li>
			즐겨찾기
		</li>
		<li>
			설정
		</li>
	</ul>
</nav>
</c:if>
</header>
