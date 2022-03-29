const type_link = window.location.href.replace(window.location.origin,'');

// 즐겨찾기 토글
const bookmark_button = document.querySelector('i.bi.bookmark');
function toggleBookmark() {
	$.ajax({
		url:'/ajax/toggle/bookmark' + type_link,
		type:'post',
		dataType:'json',
		cache:false,
		timeout:10000,
		success:function(param) {
			if(param.result=='logout') {
				alert('로그인 후 즐겨찾기할 수 있습니다!');
			}
			else if(param.result=='inserted') {
				bookmark_button.classList.replace('bi-bookmark', 'bi-bookmark-fill');
			}
			else if(param.result=='deleted') {
				bookmark_button.classList.replace('bi-bookmark-fill', 'bi-bookmark');
			}
			else {
				alert('즐겨찾기시 오류가 발생했습니다!');
			}
		}, // end of success
		error:function() {
			alert('네트워크 오류가 발생했습니다!');
		}		
	}); // end of ajax
}