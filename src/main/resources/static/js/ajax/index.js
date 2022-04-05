const current_url = window.location.href.replace(window.location.origin,'');
const totalElements = document.querySelector('span.total-elements').textContent;
const pagination = document.querySelector('.pagination');

// 문항 목록 최초 조회
getQuestions();

// pagination의 data-last 속성에 저장된 값보다 최근 문항만 조회하는 함수
function getMoreQuestions() {
	getQuestions(pagination.dataset.last);
}

// 문항 목록을 조회하는 함수
function getQuestions(lastQuestionId) {
	$.ajax({
		url:'/ajax' + current_url,
		data: {
			lastQuestionId:lastQuestionId,
			totalElements:totalElements
		},
		success:function(param) {
			if(!lastQuestionId) { // 첫 페이지 출력시 container 초기화
				clearChildNodes([container]);
			}
			
			// pagination의 data-last 속성에 저장된 값을 현재 불러온 목록에서 가장 최근 문항 번호로 갱신
			pagination.dataset.last = param.last;
			
			if(!param.hasMore) { // 더 이상 더보기할 문항이 없는 경우
				pagination.classList.add('display-none');
			}
			else {
				pagination.classList.remove('display-none');
			}
			
			// 조회한 목록을 화면에 출력
			render(param);
		} // end of success
	}); // end of Ajax
}