<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main data-title="${title} · ${responseDTO.title}">
<div class="title">
	${title}
</div>
<article>
	<ul>
		<li class="survey-title">
			${responseDTO.title}
		</li>
		<li class="survey-content">
			${responseDTO.content}
		</li>
		<li>
			${responseDTO.userName} <img src="${responseDTO.userProfileURL}" class="profile">
		</li>
		<li>
			<label class="label-button">
				<input type="button" onclick="toggleBookmark();">
				<c:if test="${responseDTO.hasBookmarked}">
				<i class="bi bi-bookmark-fill bookmark"></i>
				</c:if>
				<c:if test="${!responseDTO.hasBookmarked}">
				<i class="bi bi-bookmark bookmark"></i>
				</c:if>
			</label>
		</li>
		<c:if test="${!empty user}">
		<li>
			<button class="reverse-button" onclick="copySurvey();">복제하기</button>
		</li>
		</c:if>
	</ul>
</article>
<script type="text/javascript" src="/js/ajax/bookmark.js"></script>
<script type="text/javascript" src="/js/ajax/copy.js"></script>
</main>