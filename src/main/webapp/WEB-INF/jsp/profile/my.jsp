<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<div class="title">
	내 정보 보기
</div>
<article>
<div class="modal">
	<div class="modal-content grid plain">
		<div>
			<i class="bi bi-bell-fill"></i>
			<span>준비 중인 기능입니다.</span>
		</div>
		<button type="submit">확인</button>
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