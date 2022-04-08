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

// 지정한 페이지 번호로 이동하는 함수
function movePage(page) {
	// page 파라미터가 이미 있으면 해당 파라미터를 제거
	if(params.get('page')) params.delete('page');
	// 지정한 페이지 번호로 page 파라미터를 추가
	params.append('page', page);
	
	// 새 파라미터로 페이지 이동
	applyParameters();
}

// 지정한 필드와 방향으로 정렬을 적용하는 함수
function applySort(field, direction) {
	// sort 파라미터가 이미 있으면, sort 파라미터 중 지정한 필드가 포함된 파라미터를 제거
	if(params.getAll('sort')) params.remove('sort', params.getAll('sort').find(str => str.includes(field)));
	// 지정한 필드와 방향으로 sort 파라미터를 추가
	params.append('sort', field + ',' + direction);
	
	// 새 파라미터로 페이지 이동
	applyParameters();
}

// 정렬 버튼 초기 UI 세팅
document.querySelectorAll('[data-field]').forEach((element) => {
	// 즐겨찾기 목록의 경우, survey.title이 아니라 bookmarksurvey.survey.title로 정렬을 적용해야 하기 때문에, 필드명을 nested로 변경해야 함
	// user 관련 field는 일반 목록에서 이미 필드명이 nested이므로 변경할 필요 없음
	const field = element.dataset.field;
	if(link == 'bookmark' && !field.includes('user')) element.dataset.field = type + '.' + field;
	
	// 파라미터에 현재 필드가 없거나, 방향이 DESC인 경우 정렬 버튼 UI를 변경하지 않음
	const current = params.getAll('sort').find(str => str.includes(field));
	if(!current || current.includes('DESC')) return;
	
	// 파라미터에 현재 필드가 있으면 방향에 맞게 정렬 버튼 UI 변경
	const button = element.parentNode.querySelector('i.bi');
	if(current.includes('ASC')) { // 방향이 ASC인 경우
		button.className = button.className.replace('down-alt', 'up');
		element.dataset.direction = 'ASC';
	}
}); // end of forEach

// 정렬 버튼의 UI를 토글하고 새 정렬을 적용하는 함수
function toggleSort(element) {
	// 정렬 버튼 UI 토글
	const button = element.parentNode.querySelector('i.bi');
	if(element.dataset.direction == 'DESC') {
		button.className = button.className.replace('down-alt', 'up');
		element.dataset.direction = 'ASC';
	}
	else {
		button.className = button.className.replace('up', 'down-alt');
		element.dataset.direction = 'DESC';
	}
	
	// 정렬 적용
	applySort(element.dataset.field, element.dataset.direction);
}