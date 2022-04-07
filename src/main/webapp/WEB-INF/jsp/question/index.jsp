<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/list.css">
<main>
<div class="title">
	${title}
</div>
<article data-survey="${survey.surveyId}">
<ul class="list-top">
	<li>
		<div class="survey-title">《${survey.title}》</div>
	</li>
	<li class="page-menu">
		<ul>
			<li>
				<input type="button" value="새로고침" onclick="getQuestions();">
			</li>
			<li>
				<input type="button" value="문항 순서 변경하기" onclick="">
			</li>
			<li>
				<input type="button" class="point-button" value="문항 추가하기" onclick="location.href = '/edit/project/${survey.surveyId}/make/question';">
			</li>
		</ul>
	</li>
</ul>
<div class="list-total notice">
	[<span class="total-elements">${survey.questions}</span>개의 문항]
</div>
<div class="list-none notice">
	조회할 내용이 없습니다.
</div>
<ul class="list-main display-none">
	<!-- 목록 헤더 시작 -->
	<li class="page-label">
		<ul class="${type}">
			<li class="sm">
				번호
			</li>
			<li class="lg">
				문항
			</li>
			<li>
				유형
			</li>
			<li>
				응답 범주 수
			</li>
			<li>
				하위 문항 수
			</li>
			<li>
				<i class="bi bi-arrow-down-up"></i>
			</li>
		</ul>
	</li>
	<!-- 목록 헤더 끝 -->
	<!-- 목록 시작 -->
	<li class="page">
	</li>
	<!-- 목록 끝 -->
	<li class="pagination">
		<button type="button" onclick="getMoreQuestions();">더보기</button>
	</li>
</ul>
</article>
<script type="text/javascript" src="/js/UIUtil.js"></script>
<script type="text/javascript" src="/js/ui/index.js"></script>
<script type="text/javascript" src="/js/ajax/index.js"></script>
<script type="text/javascript" src="/js/onload/index.js"></script>
</main>