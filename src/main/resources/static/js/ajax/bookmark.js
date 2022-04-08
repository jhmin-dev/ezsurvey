// 즐겨찾기 토글
function toggleBookmark() {
	$.ajax({
		url:'/ajax/toggle/bookmark' + location.pathname,
		success:function(param) {
			if(param.result=='logout') {
				alert('로그인 후 즐겨찾기할 수 있습니다!');
			}
			else if(param.result=='inserted') {
				toggleBookmarkButton();
				changeCurrentBookmarks(1);
			}
			else if(param.result=='deleted') {
				toggleBookmarkButton();
				changeCurrentBookmarks(-1);		
			}
			else {
				alert('즐겨찾기시 오류가 발생했습니다!');
			}
		} // end of success	
	}); // end of ajax
}

// 즐겨찾기 선택 삭제
function deleteBookmarks() {
	let bookmarks = getChecked();
	if(bookmarks.length==0) return;
	
	$.ajax({
		url:'/ajax/delete' + location.pathname,
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify(bookmarks),
		success:function(param) {
			if(param.result=='logout') {
				alert('로그인 후 삭제할 수 있습니다!');
				location.replace(location.pathname);
			}
			else if(param.result=='null') {
				alert('아무것도 선택하지 않았습니다!');
			}
			else if(param.result=='success') {
				alert('선택한 항목 ' + param.affected_rows + '건이 삭제되었습니다.');
				location.replace(location.pathname);
			}
			else {
				alert('선택한 항목 삭제시 오류가 발생했습니다!');
			}
		} // end of success
	}); // end of ajax
}

function getChecked() {
	let checked_list = document.querySelectorAll('.bookmark:checked');
	let id_array = [];
	
	for(let i=0;i<checked_list.length;i++) {
		id_array[i] = checked_list[i].dataset.bookmark;
	}
	
	return id_array;
}