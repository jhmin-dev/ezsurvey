const link = document.querySelector('form').id;
const toggle_public = document.querySelector('#public');
const toggle_share = document.querySelector('#share');
const toggle_delete = document.querySelector('#delete_link');
const visibility = document.querySelector('#visibility');
const shared = document.querySelector('#shared');
const input_link = document.querySelector('.copy-link input');

// 토글 버튼 초기 활성화
if(visibility.value == 'public') {
	toggle_public.classList.add('active');
	toggle_public.querySelector('.toggle-switch').classList.add('active');
}
else if(visibility.value == 'link_only') {
	toggle_share.classList.add('active');
	toggle_share.querySelector('.toggle-switch').classList.add('active');
}
if(input_link) {
	input_link.value = input_link.dataset.link;
	if(shared.value) {
		input_link.value += '/' + shared.value;
	}
}

// 토글 버튼 애니메이션
document.addEventListener('click', function(e) {
	let toggle_container = e.target.closest('.toggle-container');
	if(toggle_container && toggle_container.contains(e.target)) {
		// 스위치 온/오프
		let toggle_switch = toggle_container.querySelector('.toggle-switch');
		toggle_container.classList.toggle('active');
		toggle_switch.classList.toggle('active');
		
		// 공개 버튼을 클릭한 경우
		if(toggle_container === toggle_public && toggle_public.classList.contains('active')) {
			toggle_share.classList.remove('active');
			toggle_share.querySelector('.toggle-switch').classList.remove('active');			
		}
		// 링크 생성 버튼을 클릭한 경우
		if(toggle_container === toggle_share && toggle_share.classList.contains('active')) {
			toggle_public.classList.remove('active');
			toggle_public.querySelector('.toggle-switch').classList.remove('active');
			if(toggle_delete) {
				toggle_delete.classList.remove('active');
				toggle_delete.querySelector('.toggle-switch').classList.remove('active');	
			}
		}
		// 링크 삭제 버튼을 클릭한 경우
		if(toggle_container === toggle_delete) {
			if(toggle_delete.classList.contains('active')) {
				toggle_share.classList.remove('active');
				toggle_share.querySelector('.toggle-switch').classList.remove('active');
				input_link.value = input_link.dataset.link;
			}
			else {
				input_link.value += '/' + shared.value;
			}
		}
	}
});

// submit 이벤트
document.querySelector('form').addEventListener('submit', function() {
	// 전체 공개 또는 링크 공개 버튼 활성화 여부에 따라 visibility 값 설정
	const public = toggle_public.classList.contains('active'); 
	const share = toggle_share.classList.contains('active'); 
	if(public) visibility.value = 'public';
	else if(share) visibility.value = 'link_only';
	else if(!public && !share) visibility.value = 'hidden';
	
	// 링크 삭제 버튼이 활성화된 경우 shared 값 삭제
	if(toggle_delete && toggle_delete.classList.contains('active')) {
		shared.value = '';
	}
});

// 링크 복사 버튼
const button_link = document.querySelector('.copy-link i.bi.button');
function copyToClipboard() {
	// window.location.origin: 프로토콜과 포트 번호 포함
	// window.location.hostname: 프로토콜과 포트 번호 제외
	navigator.clipboard.writeText(window.location.origin + input_link.value).then(() => {
		button_link.classList.replace('bi-clipboard2-fill', 'bi-clipboard2-check-fill');
	});
}

// 삭제 버튼
const button_delete = document.querySelector('#delete');
if(button_delete) {
	button_delete.onclick = function() {
		$.ajax({
			url:'/ajax/delete/project',
			type:'post',
			data:{
				survey:button_delete.dataset.survey,
			},
			dataType:'json',
			cache:false,
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 삭제할 수 있습니다!');
				}
				else if(param.result=='wrongAccess') {
					alert('잘못된 접근입니다!');
				}
				else if(param.result=='success') {
					alert('설문조사가 삭제되었습니다!');
					location.replace('/my/project');
				}
				else {
					alert('설문조사 삭제시 오류가 발생했습니다!');
				}
			}, // end of success
			error:function(error) {
				alert('네트워크 오류가 발생했습니다!');
			}
		}); // end of ajax
	}
}