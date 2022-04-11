<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	설정
</div>
<article>
<div class="modal">
	<div class="modal-content grid choose">
		<div>
			<i class="bi bi-bell-fill"></i>
			<span>준비 중인 기능입니다.</span>
		</div>
		<button type="submit" class="close-button">확인</button>
		<button type="button" onclick="location.href = '/delete/profile'">계정 탈퇴</button>
	</div>
</div>
</article>
<script type="text/javascript" src="/js/UIUtil.js"></script>
<script type="text/javascript">
	window.addEventListener('load', function() {
		openModal();
	});
	document.addEventListener('click', function(e) {
		closeModal(e, '/');
	});
</script>
</main>