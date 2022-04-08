function deleteUser(userId) {
	$.ajax({
		url:'/ajax' + location.pathname,
		data:{
			userId:userId
		},
		success:function(param) {
			if(param.result=='logout') {
				alert('로그인 후 탈퇴할 수 있습니다!');
				location.reload();
			}
			else if(param.result=='wrongAccess') {
				alert('유효하지 않은 세션입니다!');
				location.replace('/logout');
			}
			else if(param.result=='success') {
				alert('계정 탈퇴가 완료되었습니다.')
				location.replace('/logout');
			}
			else {
				alert('탈퇴시 오류가 발생했습니다!');
				location.reload();
			}
		}
	}); // end of ajax
}