const link = document.querySelector('form').id;
const toggle_public = document.querySelector('#public');
const toggle_share = document.querySelector('#share');
const toggle_delete = document.querySelector('#delete_link');
const visibility = document.querySelector('#visibility');
const shared = document.querySelector('#shared');
const input_link = document.querySelector('.copy-link input');

// 토글 버튼 초기 활성화
if(visibility.value == 'public') {
	addActive(toggle_public);
}
else if(visibility.value == 'link_only') {
	addActive(toggle_share);
}
if(input_link) {
	input_link.value = input_link.dataset.link;
	if(shared.value) { // UUID 값이 존재하면
		input_link.value += '/' + shared.value;
	}
}

// 토글 버튼 스위치 온/오프
document.addEventListener('click', function(e) {
	let toggle_container = e.target.closest('.toggle-container');
	if(toggle_container && toggle_container.contains(e.target)) {		
		// 전체 공개 버튼을 클릭하여 활성화한 경우
		if(toggle_container === toggle_public && isActive(toggle_public)) {
			removeActive(toggle_share); // 링크 공개 비활성화			
		}
		
		// 링크 공개 버튼을 클릭하여 활성화한 경우
		if(toggle_container === toggle_share && isActive(toggle_share)) {
			removeActive(toggle_public); // 전체 공개 비활성화
			if(toggle_delete && isActive(toggle_delete)) { // 링크 삭제 버튼이 존재하고 활성화되어 있으면
				removeActive(toggle_delete); // 링크 삭제 비활성화하고
				input_link.value = input_link.dataset.link + '/' + shared.value; // UUID 노출
			}
		}
		
		// 링크 삭제 버튼을 클릭한 경우
		if(toggle_container === toggle_delete) {
			if(isActive(toggle_delete)) { // 활성화시
				removeActive(toggle_share); // 링크 공개 비활성화하고
				input_link.value = input_link.dataset.link; // UUID 숨김
			}
			else { // 비활성화시
				input_link.value = input_link.dataset.link + '/' + shared.value; // UUID 노출
			}
		}
		
		// 전체 공개 또는 링크 공개 버튼 활성화 여부에 따라 visibility 값 설정
		setVisibility();
	}
});

function isActive(element) {
	return element.classList.contains('active');
}

function addActive(container) {
	container.classList.add('active');
	container.querySelector('.toggle-switch').classList.add('active');
}

function removeActive(container) {
	container.classList.remove('active');
	container.querySelector('.toggle-switch').classList.remove('active');
}

// 전체 공개 또는 링크 공개 버튼 활성화 여부에 따라 visibility 값 설정하는 함수
function setVisibility() {
	if(isActive(toggle_public)) visibility.value = 'public';
	else if(isActive(toggle_share)) visibility.value = 'link_only';
	else visibility.value = 'hidden';	
}

// submit 이벤트
document.querySelector('form').addEventListener('submit', function() {
	// 전체 공개 또는 링크 공개 버튼 활성화 여부에 따라 visibility 값 설정
	setVisibility();
	
	// 링크 삭제 버튼이 활성화된 경우 shared 값 삭제
	if(toggle_delete && isActive(toggle_delete)) {
		shared.value = '';
	}
});

// 링크 복사 버튼
const button_link = document.querySelector('.copy-link i.bi.button');
function copyToClipboard() {
	// window.location.origin: 프로토콜과 포트 번호 포함
	// window.location.hostname: 프로토콜과 포트 번호 제외
	navigator.clipboard.writeText(window.location.origin + input_link.value).then(() => {
		button_link.classList.replace('bi-clipboard2-fill', 'bi-clipboard2-check-fill');
	});
}