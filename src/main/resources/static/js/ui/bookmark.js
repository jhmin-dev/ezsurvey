const bookmark_button = document.querySelector('i.bi.bookmark');

// 즐겨찾기 버튼을 토글하는 함수
function toggleBookmarkButton() {
	bookmark_button.classList.toggle('bi-bookmark');
	bookmark_button.classList.toggle('bi-bookmark-fill');
}

// 현재 즐겨찾기된 수를 인자로 전달된 값만큼 변경하는 함수
function changeCurrentBookmarks(value) {
	const bookmarks = document.querySelector('.bookmarks');
	const currentBookmarks = Number(bookmarks.textContent); // 함수 실행시마다 새로 값을 읽어와야 하므로 지역 변수로 선언
	
	bookmarks.textContent = currentBookmarks + value; // 현재 값과 삽입/삭제를 의미하는 값 합산
	if(currentBookmarks == 0 || bookmarks.textContent == 0) { // 처음에 0이었거나 합산 후 0이 된 경우
		bookmarks.parentNode.classList.toggle('display-none'); // 현재 즐겨찾기 수 표시 여부 변경
	}
}

// 즐겨찾기 버튼 초기 활성화
if(bookmark_button && bookmark_button.dataset.hasBookmarked == 'true') {
	toggleBookmarkButton();
}