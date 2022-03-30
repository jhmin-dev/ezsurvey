<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	설문조사 상세
</div>
<article>
	<ul>
		<li>
			${responseDTO.title}
		</li>
		<li>
			${responseDTO.content}
		</li>
		<li>
			${responseDTO.userName}
		</li>
		<li>
			<label class="label-button">
				<input type="button" onclick="toggleBookmark()">
				<c:if test="${responseDTO.hasBookmarked}">
				<i class="bi bi-bookmark-fill bookmark"></i>
				</c:if>
				<c:if test="${!responseDTO.hasBookmarked}">
				<i class="bi bi-bookmark bookmark"></i>
				</c:if>
			</label>
		</li>
	</ul>
</article>
<script type="text/javascript" src="/js/detail.js"></script>
</main>