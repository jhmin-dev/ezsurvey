<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	Spring Starter Project
</div>
<article>
<!-- 검색 시작 -->
<form action="/project" id="search">
	<input type="hidden" name="field" value="title">
	<ul>
		<li>
			<input type="text" name="word">
		</li>
		<li>
			<label class="label-button">
				<i class="bi bi-search button"></i>
				<input type="submit">
			</label>
		</li>
	</ul>
</form>
<!-- 검색 끝 -->
</article>
</main>