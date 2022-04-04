const survey = document.querySelector('article').dataset.survey;
const container = document.querySelector('ul.list-main li.page');
const list_main = document.querySelector('.list-main');
const list_none = document.querySelector('.list-none');
const pagination = document.querySelector('.pagination');

// pagination의 data-last 속성에 저장된 값보다 최근 문항만 조회하는 함수
function getMoreQuestions() {
	getQuestions(pagination.dataset.last);
}

// 목록을 화면에 출력하는 함수
function render(data) {
	// 문서 조각 생성
	const fragment = document.createDocumentFragment();
	
	// 노드 추가 순환
	data.list.forEach(function(element) {
		// 구조 분해 할당
		const {question, varlabel, content, category, items, subquestions} = element;
		// 목록으로 출력할 객체
		const dto = {question, varlabel, category, items, subquestions};

		// ul 요소를 생성하고 문서 조각으로 이동
		let ul = document.createElement('ul');
		fragment.append(ul);
		ul.classList.add('page-element');

		// li 요소를 생성하고 문서 조각으로 이동
		for(key in dto) {
			let li = document.createElement('li');
			ul.append(li);

			if(key=='varlabel') { // 변수명의 경우
				li.classList.add('lg')

				let a = document.createElement('a');
				li.append(a);

				a.href = '/edit/project/' + survey + '/edit/question/' + dto['question'];
				a.textContent = dto[key] || content; // 변수명이 비어 있으면 문항 내용으로 대체
			}
			else if(key=='category') { // 문항 유형의 경우
				let span = document.createElement('span');
				li.append(span);

				span.classList.add('category');
				span.dataset.category = dto[key];
				span.textContent = data.category.find(c => c.key == dto[key]).name;
			}
			else { // 문항 유형과 변수명을 제외한 모든 열에 대해서
				li.textContent = dto[key];
			}
			
			if(key=='question') li.classList.add('sm')
		}

		// 드래그 박스용 li 요소를 생성하고 문서 조각으로 이동
		let li = document.createElement('li');
		ul.append(li);
		li.classList.add('draggable')
	}); // end of forEach

	// 문서 조각을 DOM에 추가
	container.append(fragment);

	// 목록 노출
	list_none.classList.add('display-none');
	list_main.classList.remove('display-none');
}
