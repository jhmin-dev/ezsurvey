// 밀리초 단위까지의 시각으로부터 현재 기준 경과 시간을 계산하고, 초 단위 이하는 버림
const dates = document.querySelectorAll('.date');
for(const date of dates) {
	date.textContent = getTimeSince(date.dataset.created);
	date.title = date.dataset.created.split('.')[0].replace('T',' ');
}

// 공개 범위를 아이콘으로 표시
const v_all = document.querySelectorAll('.visibility');
for(const v of v_all) {
	if(v.dataset.visibility=='public') v.classList.add('bi-unlock-fill')
	else if(v.dataset.visibility=='link_only') v.classList.add('bi-link-45deg');
	else if(v.dataset.visibility=='hidden') v.classList.add('bi-lock-fill');
	else v.classList.add('bi-trash3-fill');
}