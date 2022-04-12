function copySurvey() {
	// 모달 열기
	openModal();
	
	// 진행 바 생성
	const circle = callProgressBarCircle('#progressbar', 2000, true);
	modal.querySelector('#progressbar').classList.remove('display-none');
	
	$.ajax({
		url:'/ajax/copy' + location.pathname,
		success:function(param) {
			// 진행 바 제거
			circle.destroy();
			modal.querySelector('#progressbar').classList.add('display-none');
			
			if(param.result=='success') {
				// 모달 내 문구와 아이콘 변경
				modal.querySelector('span').textContent = '복제가 완료되었습니다!';
				modal.querySelector('i.bi').classList.remove('bi-exclamation-triangle-fill');
				modal.querySelector('i.bi').classList.add('bi-check');
				
				// 모달 닫기 버튼에 연결된 함수 변경
				modal.querySelector('.close-button').onclick = function() {
					closeModalByButton(true, '/edit/project/' + param.cloneId);
				};
			}
			else {
				// 모달 내 문구와 아이콘 변경
				isLoggedOut = param.result=='logout';
				if(isLoggedOut) {
					modal.querySelector('span').textContent = '로그인 후 이용해주세요!';
				}
				else {
					modal.querySelector('span').textContent = '설문조사 복제시 오류가 발생했습니다!';
				}
				modal.querySelector('i.bi').classList.remove('bi-check');
				modal.querySelector('i.bi').classList.add('bi-exclamation-triangle-fill');
				
				// 모달 닫기 버튼에 연결된 함수 변경
				modal.querySelector('.close-button').onclick = function() {
					closeModalByButton(false, isLoggedOut ? 'reload' : 'doNothing');
				}
			}
			
			// 모달 내 문구와 확인 버튼 보이게 변경
			modal.querySelector('i.bi').classList.remove('display-none');
			modal.querySelector('button').classList.remove('display-none');
		},
		error:function() {
			// 진행 바 제거
			circle.destroy();
		}
	}); // end of Ajax
}