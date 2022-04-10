<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main>
<article class="features">
	<div class="title">
		기능 소개
	</div>
	<ul>
		<li>
			ezsurvey는 간편하게 설문조사를 제작, 공유할 수 있는 사이트입니다.
		</li>
		<li>
			선다형, 척도형, 단답형 등 기본적인 문항 유형이 제공됩니다.
		</li>
		<li>
			전체 공개로 설정된 설문조사는 누구나 복제하여 손쉽게 수정본을 만들 수 있습니다.
		</li>
	</ul>
	<button type="button" class="point-button" onclick="location.href = '/make/project';">지금 시작하기</button>
</article>
<article class="recent">
	<div class="title">
		둘러보기
	</div>
	<!-- 검색 시작 -->
	<form action="/project" id="search">
		<input type="hidden" name="field" value="title">
		<ul>
			<li>
				<input type="search" name="word">
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
	<ul class="recent-list">
		<c:forEach items="${page}" var="survey">
		<li>
			<div onclick="location.href = '/project/${survey.surveyId}';">${survey.title}</div>
			<div data-date="${survey.created}"></div>
		</li>
		</c:forEach>
	</ul>
	<button type="button" class="reverse-button" onclick="location.href = '/project';">
		<i class="bi bi-three-dots"></i>
	</button>
</article>
<script type="text/javascript" src="/js/StringUtil.js"></script>
<script type="text/javascript" src="/js/ui/banner.js"></script>
</main>