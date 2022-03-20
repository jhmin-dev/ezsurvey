const providers = ['google', 'kakao', 'naver'];
for(let i=0;i<providers.length;i++) {
	document.getElementById(providers[i]).onclick = function() {
		location.href = '/oauth2/authorization/' + providers[i];
	}
}