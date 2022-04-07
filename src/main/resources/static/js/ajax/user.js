function deleteUser(userId) {
	$.ajax({
		url:'/ajax/delete/user',
		data:{
			userId:userId
		},
		success:function(param) {
			if(param.result=='logout') {
				alert('로그인 후 탈퇴할 수 있습니다!');
				location.reload();
			}
			else if(param.result=='wrongAccess') {
				alert('다른 사람의 계정을 탈퇴할 수 없습니다!');
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