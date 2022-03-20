<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
<div class="header-container-top"></div>
<div class="header-container">
	<div class="logo">
		<a href="/">logo</a>
	</div>
	<div class="title">
		<a href="/">ezsurvey</a>
	</div>
	<div class="hamburger">
		<div class="hamburger-button icon-menu">
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>
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
				<a href="/my/project">
					내 설문조사
				</a>
			</li>
			<c:if test="${!empty user}">
			<li>
				<a class="not-hover" href="/make/project">
					<div class="plus-button icon-menu">
						<div></div>
						<div></div>
					</div>
				</a>
			</li>
			<li>
				<div class="icon-menu">
					<img class="profile" src="${user.profileURL}">
				</div>
			</li>
			</c:if>	
		</ul>
	</nav>
</div>
<nav class="dropdown-menu display-none">
	<c:if test="${!empty user}">
	<div class="tail">
		<i class="bi bi-triangle-fill"></i>
	</div>
	<ul>
		<li class="not-link">
			${user.name}님
		</li>
		<li class="not-link"><hr></li>
		<li>
			<a href="/my/profile">내 정보 보기</a>
		</li>
		<li class="not-link"><hr></li>
		<li class="not-link">
			즐겨찾기
		</li>
		<li class="sub">
			<a href="/bookmark/project">설문조사</a>
		</li>
		<li class="sub">
			<a href="/bookmark/question">문항</a>
		</li>
		<li class="not-link"><hr></li>
		<li>
			<a href="/settings/profile">설정</a>
		</li>
	</ul>
	</c:if>
</nav>
<script type="text/javascript" src="/js/menu.js"></script>
</header>