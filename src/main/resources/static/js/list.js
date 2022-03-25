// 밀리초 단위까지의 시각으로부터 현재 기준 경과 시간을 계산하고, 초 단위 이하는 버림
const dates = document.querySelectorAll('.date');
for(const date of dates) {
	date.textContent = getTimeSince(date.dataset.created);
	date.title = date.dataset.created.split('.')[0].replace('T',' ');
}