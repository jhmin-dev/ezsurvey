const login_buttons = document.querySelectorAll('.login-button');
for(let i=0;i<login_buttons.length;i++) {
	let l = login_buttons[i];
	// 버튼에 인증 링크 연결
	l.onclick = function() {
		location.href = '/oauth2/authorization/' + l.id;
	}
	// 조사 자동 선택
	l.querySelector('span.post').textContent
		= getPostposition(l.querySelector('span.word').textContent, '으로/로');
}