const link = document.querySelector('article').dataset.link;
const selectCategory = document.querySelector('ul.question-type select[name="category"]');
const inputItems = document.querySelector('ul.question-type input[name="items"]');
const content = document.querySelector('li.content');
const itemContainer = document.querySelector('li.item-container');
const itemContainerLabel = document.querySelector('li.item-container-label');

// Placeholder
const varlabelPlaceholder = '선거 관심';
const contentPlaceholder = '선생님께서는 이번 선거에 얼마나 관심이 있으십니까?';
const fakeItemPlaceholder = '응답 내용';
const vallabelChoicePlaceholders = ['매우 관심이 있었다', '조금 관심이 있었다', '별로 관심이 없었다', '전혀 관심이 없었다'];
const vallabelScalePlaceholders = ['매우 그렇다', '전혀 그렇지 않다'];

function initialize(link) {
	if(link=='make') initializeMake();
	else if(link=='edit') {
		getQuestionToEdit();
	}
}

// 문항 추가하는 경우의 초기화 함수
function initializeMake(hasChildNodes) {
	if(hasChildNodes) clearChildNodes([content, itemContainer]);
	content.append(createFormQuestion());
	resizeFormItems(setInputCategory());
	if(hasChildNodes) initializeSummernote(); // summernote 편집기 초기화
}

// 문항 수정하는 경우의 초기화 함수
function initializeEdit(data) {
	// 문항 태그 만들기
	// 주의: content의 경우 같은 이름 변수가 이미 있어서 구조 분해 할당시 append 불가능해짐
	const {category, startFromOne, varlabel} = data.question;
	const formQuestion = createFormQuestion(category, startFromOne, varlabel, data.question.content);
	content.append(formQuestion);
	initializeSummernote(); // summernote 편집기 초기화
	
	// 문항 유형 및 응답 범주 수 초기화
	selectCategory.value = category;
	setInputCategory(data.itemList.length);
	
	// 응답 범주 태그 만들기
	data.itemList.forEach(function(element) {
		const {itemId, value, vallabel} = element;
		itemContainer.append(createFormItem(value, itemId, vallabel));
	}); // end of forEach
}

// 문항 form 태그를 생성하는 함수
function createFormQuestion(category, startFromOne, varlabel, content) {
	const formQuestion = Object.assign(document.createElement('form'), {name:'question'});
	
	let fragmentHidden = document.createDocumentFragment();
	fragmentHidden.append(Object.assign(document.createElement('input'), {
		name:'category',
		type:'hidden',
		value:category||''
	}));
	fragmentHidden.append(Object.assign(document.createElement('input'), {
		name:'startFromOne',
		type:'hidden',
		value:startFromOne||'true'
	}));
	formQuestion.append(fragmentHidden);
	
	let divVarlabel = document.createElement('div');
	divVarlabel.append(Object.assign(document.createElement('label'), {
		htmlFor:'varlabel',
		textContent:'변수명'
	}));
	divVarlabel.append(Object.assign(document.createElement('input'), {
		name:'varlabel',
		type:'text',
		placeholder:varlabelPlaceholder,
		value:varlabel||''
	}));
	divVarlabel.append(Object.assign(document.createElement('div'), {
		textContent:'변수명은 생략 가능하며, 최대 256자까지만 입력 가능합니다.',
		className:'notice'
	}));
	formQuestion.append(divVarlabel);
	
	let divContent = document.createElement('div');
	divContent.append(Object.assign(document.createElement('label'), {
		htmlFor:'content',
		textContent:'문항'
	}));
	divContent.append(Object.assign(document.createElement('textarea'), {
		name:'content',
		className:'summernote',
		placeholder:contentPlaceholder,
		value:content||''
	}));
	divContent.append(Object.assign(document.createElement('div'), {
		textContent:'문항 번호는 자동으로 부여됩니다.',
		className:'notice'
	}));
	formQuestion.append(divContent);

	return formQuestion;
}

// 응답 범주 form 태그를 생성하는 함수
function createFormItem(i, itemId, vallabel) {
	const formItem = Object.assign(document.createElement('form'), {name:'item'});
	
	// 응답 범주 값
	formItem.append(Object.assign(document.createElement('input'), {
		name:'value',
		type:'text',
		value:i,
		readOnly: true
	}));
	
	// 응답 범주 라벨
	formItem.append(Object.assign(document.createElement('input'), {
		name:'vallabel',
		type:'text',
		value:vallabel||''
	}));
	
	// 응답 범주 PK
	if(itemId) {
		formItem.append(Object.assign(document.createElement('input'), {
			name:'itemId',
			type:'hidden',
			value:itemId
		}));		
	}
		
	return formItem;
}

// 가짜 응답 범주 form 태그를 생성하는 함수
function createFormFakeItem() {
	const formFakeItem = Object.assign(document.createElement('form'), {name:'fakeItem'});
	
	let fakeItemInput = Object.assign(document.createElement('input'), {
		type:'text',
		placeholder:fakeItemPlaceholder
	});
	formFakeItem.append(fakeItemInput);
		
	return formFakeItem;
}

// 문항 유형 input 태그의 값을 문항 유형 select 태그에서 선택된 값으로 변경하고, 유형에 맞게 응답 범주 수 input 태그 값과 응답 범주 form 태그 수를 변경하는 함수
function setInputCategory(items) {
	const inputCategory = document.querySelector('form[name="question"] input[name="category"]');
	inputCategory.value = selectCategory.options[selectCategory.selectedIndex].value;
	itemContainer.className = 'item-container'; // item-container 외의 다른 클래스를 모두 제거
	itemContainer.classList.add(inputCategory.value.replace('_','-')); // 선택된 문항 유형명을 클래스로 추가
	
	if(inputCategory.value=='multiple_choice') { // 선다형 기본값
		inputItems.value = items || 4;
		inputItems.min = 1;
		inputItems.disabled = false;
	}
	else if(inputCategory.value=='likert_scale') { // 척도형 기본값
		inputItems.value = items || 7;
		inputItems.min = 4;
		inputItems.disabled = false;
	}
	else if(inputCategory.value=='short_answer') { // 단답형 기본값
		inputItems.value = '';
		inputItems.min = 1;
		inputItems.disabled = true;
	}
	
	return inputItems.value;
}

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
	
	itemContainerLabel.classList.remove('display-none');
}