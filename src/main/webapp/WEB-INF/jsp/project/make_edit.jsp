<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<main>
<div class="title">
	${title}
</div>
<article>
<form:form modelAttribute="requestDTO" id="${link}">
	<form:errors cssClass="error notice" element="div"/>
	<form:hidden path="visibility"/>
	<form:hidden path="shared"/>
	<ul>
		<li>
			<form:label path="title">제목</form:label>
			<form:input path="title"/>
			<form:errors path="title" cssClass="error notice" element="div"/>
		</li>
		<li>
			<form:label path="content">내용</form:label>
			<form:textarea path="content"/>
			<form:errors path="content" cssClass="error notice" element="div"/>
		</li>
		<li class="toggle">
			<label>전체 공개</label>
			<div class="toggle-container" id="public">
				<div class="toggle-switch"></div>
			</div>
			<div class="notice">
				설문조사를 전체 공개합니다.
				<a href="/project">둘러보기</a>에 내 설문조사가 나타납니다.
			</div>
		</li>
		<li class="toggle">
			<label>링크 공개</label>
			<div class="toggle-container" id="share">
				<div class="toggle-switch"></div>
			</div>
			<div class="notice">
				설문조사를 링크 받은 사람에게만 공개합니다.<br>
				<c:if test="${!empty requestDTO.shared}">
				비활성화하더라도 이미 생성된 링크는 유지됩니다.<br>
				나중에 링크 공개를 다시 활성화하면 현재 링크가 재사용됩니다.
				</c:if>
			</div>
		</li>
		<c:if test="${!empty requestDTO.shared}">
		<li class="toggle">
			<label>링크 삭제</label>
			<div class="toggle-container" id="delete_link">
				<div class="toggle-switch"></div>
			</div>
			<div class="notice">
				현재 생성되어 있는 링크를 삭제합니다.<br>
				나중에 링크 공개를 다시 활성화하더라도 현재 링크를 재사용할 수 없습니다.
			</div>
		</li>
		</c:if>
		<c:if test="${link eq 'edit'}">
		<li>
			<label>링크 복사</label>
			<div class="copy-link">
			<input type="text" data-link="/project/${requestDTO.survey}/preview" readonly>
			<div onclick="copyToClipboard();">
				<i class="bi bi-clipboard2-fill button"></i>
			</div>
			</div>
		</li>
		<li class="edit-menu">
			<hr>
			<button type="button" class="reverse-button">
				<i class="bi bi-calendar-week"></i>
				<span>배포 날짜 설정</span>
			</button>
			<hr>
			<button type="button" class="reverse-button" onclick="location.href = '/edit/project/${requestDTO.survey}/index';">
				<i class="bi bi-pencil-square"></i>
				<span>문항 관리</span>
			</button>
			<hr>
		</li>
		</c:if>
		<li class="form-menu">
			<c:if test="${link eq 'make'}">
			<input type="submit" value="생성하기">
			</c:if>
			<c:if test="${link eq 'edit'}">
			<input type="submit" value="수정하기">
			<input type="button" value="삭제하기" id="delete" data-survey="${requestDTO.survey}">
			</c:if>
			<input type="button" value="이전으로" onclick="history.go(-1);">
		</li>
	</ul>
</form:form>
</article>
<link rel="stylesheet" href="/css/survey.css">
<script type="text/javascript" src="/js/ui/survey.js"></script>
<script type="text/javascript" src="/js/ajax/survey.js"></script>
</main>