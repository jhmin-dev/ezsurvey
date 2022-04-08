function deleteSurvey() {
	$.ajax({
		url:'/ajax' + location.pathname.replace('edit', 'delete'),
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
		} // end of success
	}); // end of ajax
}