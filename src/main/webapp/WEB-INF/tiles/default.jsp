<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title"/></title>
<link rel="stylesheet" href="/css/common.css">
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