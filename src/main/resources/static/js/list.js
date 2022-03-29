const article= document.querySelector('article');
const type = article.dataset.type;
const link = article.dataset.link;
const type_link = window.location.href.replace(window.location.origin,'');

// 밀리초 단위까지의 시각으로부터 현재 기준 경과 시간을 계산하고, 초 단위 이하는 버림
const dates = document.querySelectorAll('.date');
for(const date of dates) {
	date.textContent = getTimeSince(date.dataset.created);
	date.parentNode.title = date.dataset.created.split('.')[0].replace('T',' ');
}

// 공개 범위를 아이콘으로 표시
if(link=='my') {
	for(const v of document.querySelectorAll('.visibility')) {
		if(v.dataset.visibility=='public') {
			v.classList.add('bi-unlock-fill');
			v.parentNode.title = '전체 공개';
		}
		else if(v.dataset.visibility=='link_only') {
			v.classList.add('bi-link-45deg');
			v.parentNode.title = '링크 공개';
		}
		else if(v.dataset.visibility=='hidden') {
			v.classList.add('bi-lock-fill');
			v.parentNode.title = '비공개';
		}
		else {
			v.classList.add('bi-trash3-fill');
			v.parentNode.title = '삭제';
		}
	}
}

// 즐겨찾기 선택 삭제
function deleteBookmarks() {
	let bookmarks = getChecked();
	if(bookmarks.length==0) return;
	
	$.ajax({
		url:'/ajax/delete' + type_link,
		type:'post',
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify(bookmarks),
		dataType:'json',
		cache:false,
		timeout:10000,
		success:function(param) {
			if(param.result=='logout') {
				alert('로그인 후 삭제할 수 있습니다!');
				location.replace(type_link);
			}
			else if(param.result=='null') {
				alert('아무것도 선택하지 않았습니다!');
			}
			else if(param.result=='success') {
				alert('선택한 항목 ' + param.affected_rows + '건이 삭제되었습니다.');
				location.replace(type_link);
			}
			else {
				alert('선택한 항목 삭제시 오류가 발생했습니다!');
			}
		}, // end of success
		error:function() {
			alert('네트워크 오류가 발생했습니다!');
		}		
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