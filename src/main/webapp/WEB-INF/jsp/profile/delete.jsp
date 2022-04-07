<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/css/user.css">
<main>
<div class="title">
	회원 탈퇴
</div>
<article>
<div class="delete-profile">
<ul class="list-style-exclamation">
	<li>
		<label>계정에 대해서</label>
	</li>
	<li>계정을 탈퇴하면 이메일 주소, 이름/별명, 프로필 이미지 등 모든 개인 정보가 <span class="font-emphasis">즉시</span> 파기됩니다.</li>
	<li>같은 이메일 주소로 다시 가입하는 것은 허용됩니다.</li>
	<li>새로 가입한 계정은 이미 탈퇴한 계정과 완전히 다른 계정이므로, 새 계정에서 옛 계정의 설문조사를 수정하거나 삭제할 수 없습니다.</li>
</ul>
<ul class="list-style-exclamation">
	<li>
		<label>설문조사에 대해서</label>
	</li>
	<li>계정을 탈퇴하더라도, 이미 생성한 <span class="font-emphasis">설문조사가 자동으로 삭제되지는 않습니다.</span><br>
		탈퇴 후 설문조사가 다른 사람들에게 보이는 것을 원치 않는다면, <a href="/my/project">내 설문조사</a>에서 직접 삭제하거나 공개 범위를 변경하세요.
	</li>
	<li>탈퇴 후에도 전체 공개인 설문조사는 계속해서 <a href="/project">둘러보기</a>에 나타납니다.<br>
		단, 이름/별명과 프로필 이미지 정보는 파기되므로, 설문조사 생성자는 탈퇴한 계정(👻)으로 표시될 것입니다.
	</li>
</ul>
<ul class="list-style-exclamation">
	<li>
		<label>설문조사에 대해서 (2)</label>
	</li>
	<li>계정을 탈퇴하면, 현재 계정에서 생성했던 설문조사에 대한 모든 권한을 <span class="font-emphasis">영구적으로</span> 잃어버리게 됩니다.</li>
	<li>탈퇴 후에 관리자에게 문의하더라도, 서버에 옛 계정의 이메일 주소가 보관되어 있지 않기 때문에, <span class="font-emphasis">설문조사 생성자가 맞는지 인증하는 것이 불가능</span>합니다.</li>
</ul>
<ul>
	<li class="toggle">
		<label>정말 탈퇴하실 건가요?</label>
		<div class="notice">탈퇴 후에는 어떤 방법으로도 옛 계정의 설문조사를 삭제할 수 없습니다.</div>
		<div class="toggle-container" onclick="toggleDeleteButton();">
			<div class="toggle-switch"></div>
		</div>
	</li>
</ul>
<ul class="display-none delete-button">
	<li>
		<button type="button" class="point-button" onclick="deleteUser(${user.userId});">탈퇴하기</button>
		<button type="button" class="reverse-button" onclick="history.go(-1);">이전으로</button>
	</li>
</ul>
</div>
</article>
<script type="text/javascript" src="/js/UIUtil.js"></script>
<script type="text/javascript" src="/js/ui/user.js"></script>
<script type="text/javascript" src="/js/ajax/user.js"></script>
</main>