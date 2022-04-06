// 접속한 링크에 따라 문항 및 응답 범주 초기화
initialize(link);

// 문항 유형 select 태그 값이 변하면 발생하는 이벤트
selectCategory.addEventListener('change', function() {
	setInputCategory();
});

// 응답 범주 수 input 태그 값이 변하면 발생하는 이벤트
inputItems.addEventListener('change', function(e) {
	resizeFormItems(e.target.value);
});