const article= document.querySelector('article');
const type = article.dataset.type;
const link = article.dataset.link;

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

// 필드 선택시 새로고침
const search = document.getElementById('search');
const field = document.querySelector('select[name="field"]');
const word = document.querySelector('input[name="word"]');
field.addEventListener('change', function() {
	if(!word.value) return;
	search.submit();
});
if(type=='question') {
	const category = document.querySelector('select[name="category"]');
	category.onchange = function() {
		search.submit();
	}
}