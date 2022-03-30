<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title><tiles:insertAttribute name="title"/></title>
<link rel="stylesheet" href="/css/common.css">
<script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
const token = document.querySelector('meta[name="_csrf"]').content;
const header = document.querySelector('meta[name="_csrf_header"]').content;
$.ajaxSetup({
	beforeSend:function(xhr) {
		xhr.setRequestHeader(header, token);
		xhr.setRequestHeader("X-Ajax-Call", "true"); // AccessDenied 처리시 Ajax 접근을 구분하기 위한 헤더
	},
	type:'POST',
	dataType:'JSON',
	timeout:10000,
	error:function(jqXHR, exception) {
		console.log(jqXHR.statusText);
		if(jqXHR.status==403) {
			const result = $.parseJSON(jqXHR.responseText).result;
			if(result=='logout') {
				alert('세션이 유효하지 않습니다! 로그인 후 이용해주세요.');
				location.replace('/login');
			}
			else if(result=='login') {
				alert('세션이 유효하지 않습니다!');
				location.reload();
			}
		}
		else {
			alert('네트워크 오류가 발생했습니다!');
		}
	}
}); // ajax 공통 옵션
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
</body>
</html>