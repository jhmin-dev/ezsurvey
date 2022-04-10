<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/css/detail.css">
<main data-title="${title} · ${responseDTO.title}">
<div class="title">
	<div>${title}</div>
	<div class="survey-title">
		《${responseDTO.title}》
	</div>
</div>
<article>
<ul class="survey-header">
	<li class="no-padding"><hr></li>
	<li>
		<label>문항 수</label>
		<div>${responseDTO.questions}</div>
	</li>
	<li>
		<label>응답자 수</label>
		<div class="notice">준비 중인 기능입니다.</div>
	</li>
	<li>
		<label>배포 시작일</label>
		<div class="date" data-date="${responseDTO.distributed}"></div>
	</li>
	<li>
		<label>배포 종료일</label>
		<div class="date" data-date="${responseDTO.expires}"></div>
	</li>
	<li>
		<label>미리보기 링크</label>
		<a class="preview-link" <c:if test="${!empty user && user.userId eq responseDTO.userId}">data-visibility="${responseDTO.visibility}" data-shared="${responseDTO.shared}"</c:if>>
		</a>
	</li>
	<c:if test="${!empty user && user.userId eq responseDTO.userId}">
	<li>
		<label>배포 링크</label>
		<div class="notice">준비 중인 기능입니다.</div>
	</li>
	<li class="survey-menu">
		<label class="label-button download disabled">
			<input type="button" onclick="">
			설문 결과 저장
			<i class="bi bi-download"></i>
		</label>
		<label class="label-button print disabled">
			<input type="button" onclick="">
			설문지 인쇄
			<i class="bi bi-printer-fill"></i>
		</label>
		<label class="label-button edit">
			<input type="button" onclick="location.href = '/edit/project/${responseDTO.surveyId}';">
			설문조사 수정
			<i class="bi bi-gear-fill"></i>
		</label>
	</li>
	</c:if>
	<li class="no-padding"><hr></li>
</ul>
<div class="survey-content">
	<div>${responseDTO.content}</div>
</div>
<ul class="survey-footer">
	<li class="no-padding"><hr></li>
	<li class="survey-created">
		<div class="created-by">
			<img src="${responseDTO.userProfileURL}" class="profile">
			<div <c:if test="${responseDTO.userDeleted}">class="deleted-account" title="탈퇴한 계정"</c:if>>${responseDTO.userName}</div> 
		</div>
		<div class="created-at">
			<label>등록일</label>
			<div class="date" data-date="${responseDTO.created}"></div>
		</div>
	</li>
	<li class="no-padding"><hr></li>
	<li>
		<div class="survey-bookmark">
			<label class="label-button  <c:if test="${empty user}">disabled</c:if>">
				<input type="button" onclick="toggleBookmark();">
				<i class="bi bi-bookmark bookmark" data-has-bookmarked="${responseDTO.hasBookmarked}"></i>
			</label>	
			<div <c:if test="${responseDTO.bookmarks==0}">class="display-none"</c:if>>
				<span class="bookmarks">${responseDTO.bookmarks}</span>명이 즐겨찾기함
			</div>
		</div>
		<div class="survey-menu">
			<button type="button" class="reverse-button" onclick="copySurvey();" <c:if test="${empty user}">disabled</c:if>>복제하기</button>
			<button type="button" onclick="location.href = '/project/${responseDTO.surveyId}/preview';">미리보기</button>
			<button type="button" onclick="location.href = '/project';">목록으로</button>
		</div>
	</li>
	<li class="no-padding"><hr></li>
</ul>
</article>
<script type="text/javascript" src="/js/StringUtil.js"></script>
<script type="text/javascript" src="/js/ui/detail.js"></script>
<script type="text/javascript" src="/js/ui/bookmark.js"></script>
<script type="text/javascript" src="/js/ajax/bookmark.js"></script>
<script type="text/javascript" src="/js/ajax/copy.js"></script>
</main>