// 인자로 전달된 노드의 모든 자식 태그를 삭제하는 함수
function clearChildNodes(parents) {
	for(parent of parents) {
		while(parent.hasChildNodes()) {
			parent.removeChild(parent.lastChild);
		}
	}
}

// textarea에서 입력이 일어나면 발생하는 이벤트
document.addEventListener('input', function(e) { // 문항 태그의 경우 비동기 통신 후 삭제될 수 있어서 동적 이벤트 바인딩 필요
	if(e.target.tagName.toLowerCase()==='textarea') { // HTML 문서의 태그는 (실제 태그명을 어떻게 작성했든) tagName 값이 항상 대문자이지만, svg처럼 XML 기반인 경우에는 원래 태그명의 대소문자를 보존함
		resizeTextarea(e.target);
	}
});

// textarea 높이를 자동 조절하는 함수
function resizeTextarea(obj) {
	obj.style.height = 'auto'; // 스크롤 바를 만들고
	obj.style.height = (4 + obj.scrollHeight) + 'px'; // 그 높이만큼으로 크기 변경; 4는 스크롤바가 생기지 않는 최솟값인데 크롬, 엣지에서만 확인함
}

// input[type="text"] 및 textarea 태그의 값이 변화하면 불필요한 공백을 제거하는 이벤트
document.addEventListener('change', function(e) {
	let isInput = e.target.matches('input[type="text"]');
	let isTextarea = e.target.tagName.toLowerCase()==='textarea';
	if(isInput || isTextarea) {
		e.target.value = e.target.value.trim();
		if(isTextarea) resizeTextarea(e.target);
	}
});