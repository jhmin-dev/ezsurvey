<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<main>
<link rel="stylesheet" href="/css/login.css">
<script src="https://accounts.google.com/gsi/client" async defer></script>
<div class="title">
	로그인/회원 가입
</div>
<article>
	<div id="google" class="login-button">
		<img src="/images/logo_google.png">
		<div>구글로 로그인하기</div>
	</div>
	<div id="kakao" class="login-button">
		<img src="/images/logo_kakao.png">
		<div>카카오로 로그인하기</div>
	</div>
	<div id="naver" class="login-button">
		<img src="/images/logo_naver.png">
		<div>네이버로 로그인하기</div>
	</div>
</article>
<script type="text/javascript" src="/js/login.js"></script>
</main>