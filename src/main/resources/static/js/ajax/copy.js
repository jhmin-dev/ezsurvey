function copySurvey() {
	openModal();
	const circle = callProgressBarCircle('#progressbar', 2000, true);
	
	$.ajax({
		url:'/ajax/copy' + location.pathname,
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify({
			title:document.querySelector('.survey-title').textContent.trim(),
			content:document.querySelector('.survey-content').textContent.trim()
		}),
		success:function(param) {
			if(param.result=='success') {
				circle.destroy();
				modal.querySelector('#progressbar').classList.add('display-none');
				modal.querySelector('i.bi').classList.remove('display-none');
				modal.querySelector('span').textContent = '복제가 완료되었습니다';
				modal.querySelector('button').classList.remove('display-none');
				document.addEventListener('click', function(e) {
					closeModal(e, '/edit/project/' + param.cloneId);
				});
			}
		}
	}); // end of Ajax
}