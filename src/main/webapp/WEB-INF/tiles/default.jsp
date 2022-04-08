<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title"/></title>
<link rel="icon" href="/images/favicon.svg">
<link rel="stylesheet" href="/css/common.css">
<script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$.ajaxSetup({
	type:'POST',
	dataType:'JSON',
	timeout:10000
}); // ajax 공통 옵션
$(document).ajaxError(function() {
	alert('서버 오류가 발생했습니다!');
}); // ajax global event handler
</script>
</head>
<body>
<div class="page-container">
<!-- header 시작 -->
<tiles:insertAttribute name="header"/>
<!-- header 끝 -->
<!-- body 시작 -->
<aside class="left"></aside>
<tiles:insertAttribute name="body"/>
<aside class="right"></aside>
<!-- body 끝 -->
<!-- footer 시작 -->
<tiles:insertAttribute name="footer"/>
<!-- footer 끝 -->
</div>
<script type="text/javascript">
	const headtitle = document.querySelector('main').dataset.title;
	document.title = (headtitle || document.querySelector('main>div.title').textContent);
</script>
</body>
</html>