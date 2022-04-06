function copySurvey() {
	$.ajax({
		url:'/ajax/copy' + current_url,
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify({
			title:document.querySelector('.survey-title').textContent.trim(),
			content:document.querySelector('.survey-content').textContent.trim()
		}),
		success:function(param) {
			console.log(param.result);
			if(param.result=='success') {
				location.href = '/edit/project/' + param.cloneId;
			}
		}
	}); // end of Ajax
}