// 토글 버튼
document.addEventListener('click', function(e) {
	let toggle_container = e.target.closest('.toggle-container');
	if(toggle_container && toggle_container.contains(e.target)) {
		let toggle_switch = toggle_container.querySelector('.toggle-switch');
		toggle_container.classList.toggle('active');
		toggle_switch.classList.toggle('active');
	}
});

// submit 이벤트
document.querySelector('form#make').addEventListener('submit', function() {
	let toggle_public = document.querySelector('#public');
	let toggle_share = document.querySelector('#share');
	let visibility = document.querySelector('#visibility');
	if(toggle_public.classList.contains('active')) {
		visibility.value = 2;
	}
	if(toggle_share.classList.contains('active')) {
		visibility.value = 1;
	}
})