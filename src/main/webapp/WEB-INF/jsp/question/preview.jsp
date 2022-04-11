<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main data-title="${title} · ${responseDTO.title}">
<div class="title">
	${title}
</div>
<article>
<div class="modal">
	<div class="modal-content grid plain">
		<div>
			<i class="bi bi-bell-fill"></i>
			<span>준비 중인 기능입니다.</span>
			<br>
			<span>요청 정보</span>
			<span class="notice">《${responseDTO.title}》</span>
			<c:if test="${!empty questionId}">
			<span class="notice">문항 번호: ${questionId}</span>
			</c:if>
		</div>
		<button type="submit" class="close-button">확인</button>
	</div>
</div>
</article>
<script type="text/javascript" src="/js/UIUtil.js"></script>
<script type="text/javascript">
	window.addEventListener('load', function() {
		openModal();
	});
	document.addEventListener('click', function(e) {
		closeModal(e, '/project/${responseDTO.surveyId}');
	});
</script>
</main>