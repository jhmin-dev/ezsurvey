const link = document.querySelector('article').dataset.link;
const selectCategory = document.querySelector('ul.question-type select[name="category"]');
const inputItems = document.querySelector('ul.question-type input[name="items"]');
const content = document.querySelector('li.content');
const itemContainer = document.querySelector('li.item-container');
const varlabelPlaceholder = '선거 관심';
const contentPlaceholder = '선생님께서는 이번 선거에 얼마나 관심이 있으십니까?';
const vallabelPlaceholders = ['응답 내용', '매우 관심 있었다', '조금 관심 있었다', '별로 관심 없었다', '전혀 관심 없었다'];

// 접속한 링크에 따라 문항 및 응답 범주 초기화
initialize(link);
function initialize(link) {
	if(link=='make') initializeMake();
}

// 인자로 전달된 노드의 모든 자식 태그를 삭제하는 함수
function clearChildNodes(parents) {
	for(parent of parents) {
		while(parent.hasChildNodes()) {
			parent.removeChild(parent.lastChild);
		}
	}
}

// 문항 추가하는 경우의 초기화 함수
function initializeMake(hasChildNodes) {
	if(hasChildNodes) clearChildNodes([content, itemContainer]);
	content.append(createFormQuestion());
	setInputCategory();
}

// 문항 form 태그를 생성하는 함수
function createFormQuestion() {
	const formQuestion = Object.assign(document.createElement('form'), {name:'question'});
	
	let fragmentHidden = document.createDocumentFragment();
	fragmentHidden.append(Object.assign(document.createElement('input'), {
		name:'category',
		type:'hidden'
	}));
	fragmentHidden.append(Object.assign(document.createElement('input'), {
		name:'startFromOne',
		type:'hidden',
		value:'true'
	}));
	formQuestion.append(fragmentHidden);
	
	let divVarlabel = document.createElement('div');
	divVarlabel.append(Object.assign(document.createElement('label'), {
		htmlFor:'varlabel',
		textContent:'변수명',
	}));
	divVarlabel.append(Object.assign(document.createElement('input'), {
		name:'varlabel',
		type:'text',
		placeholder:varlabelPlaceholder
	}));
	formQuestion.append(divVarlabel);
	
	let divContent = document.createElement('div');
	divContent.append(Object.assign(document.createElement('label'), {
		htmlFor:'content',
		textContent:'문항'
	}));
	divContent.append(Object.assign(document.createElement('input'), {
		name:'content',
		type:'text',
		placeholder:contentPlaceholder
	}));
	formQuestion.append(divContent);

	return formQuestion;
}

// 응답 범주 form 태그를 생성하는 함수
function createFormItem(i) {
	const formItem = Object.assign(document.createElement('form'), {name:'item'});
	
	let divValue = document.createElement('div');
	divValue.append(Object.assign(document.createElement('label'), {
		htmlFor:'value',
		textContent:'번호',
	}));
	divValue.append(Object.assign(document.createElement('input'), {
		name:'value',
		type:'number',
		value:i
	}));
	formItem.append(divValue);
	
	let divVallabel = document.createElement('div');
	divVallabel.append(Object.assign(document.createElement('label'), {
		htmlFor:'vallabel',
		textContent:'보기'
	}));
	divVallabel.append(Object.assign(document.createElement('input'), {
		name:'vallabel',
		type:'text',
		placeholder:i<5 ? vallabelPlaceholders[i] : ''
	}));
	formItem.append(divVallabel);
		
	return formItem;
}

// 가짜 응답 범주 form 태그를 생성하는 함수
function createFormFakeItem() {
	const formFakeItem = Object.assign(document.createElement('form'), {name:'fakeItem'});
	
	let divVallabel = document.createElement('div');
	divVallabel.append(Object.assign(document.createElement('label'), {textContent:vallabelPlaceholders[0]}));
	divVallabel.append(Object.assign(document.createElement('input'), {type:'text'}));
	formFakeItem.append(divVallabel);
		
	return formFakeItem;
}

// 문항 유형 select 태그 값이 변하면 발생하는 이벤트
selectCategory.addEventListener('change', function() {
	setInputCategory();
});
// 문항 유형 input 태그의 값을 문항 유형 select 태그에서 선택된 값으로 변경하고, 유형에 맞게 응답 범주 수 input 태그 값과 응답 범주 form 태그 수를 변경하는 함수
function setInputCategory() {
	const inputCategory = document.querySelector('form[name="question"] input[name="category"]');
	inputCategory.value = selectCategory.options[selectCategory.selectedIndex].value;
	
	if(inputCategory.value=='multiple_choice') { // 선다형 기본값
		inputItems.value = 4;
		inputItems.disabled = false;
	}
	else if(inputCategory.value=='likert_scale') { // 척도형 기본값
		inputItems.value = 5;
		inputItems.disabled = false;
	}
	else if(inputCategory.value=='short_answer') { // 단답형 기본값
		inputItems.value = '';
		inputItems.disabled = true;
	}
	
	resizeFormItems(inputItems.value);
}

// 응답 범주 수 input 태그 값이 변하면 발생하는 이벤트
inputItems.addEventListener('change', function(e) {
	resizeFormItems(e.target.value);
});
// 응답 범주 form 태그의 수를 변경하는 함수
function resizeFormItems(goal) {
	const formItems = document.querySelectorAll('form[name="item"]'); // querySelectorAll()은 DOM과 별도인 정적 노드 리스트 반환
	const current = formItems.length
	const diff = goal - current;
	
	if(diff>0) { // 응답 범주 수를 증가시킨 경우
		let fragmentItems = document.createDocumentFragment();
		for(let i=1;i<=diff;i++) {
			fragmentItems.append(createFormItem(current + i));
		}
		itemContainer.append(fragmentItems);
	}
	else if(diff<0) { // 응답 범주 수를 감소시킨 경우
		for(let i=1;i<=Math.abs(diff);i++) {
			let last = formItems[current-i];
			itemContainer.removeChild(last);
		}
	}
	
	let formFakeItem = document.querySelector('form[name="fakeItem"]');
	if(formFakeItem && goal!=0) { // 가짜 응답 범주가 있고, 바꿀 응답 범주 수가 0이 아니면
		itemContainer.removeChild(formFakeItem);
	}
	else if(!formFakeItem && goal==0) { // 가짜 응답 범주가 없고, 바꿀 응답 범주 수가 0이면
		itemContainer.append(createFormFakeItem());
	}
}