// Ajax로만 서버에 전송할 것이므로 모든 form에 submit 기본 이벤트 제거
document.addEventListener('submit', function(e) {
	e.preventDefault();
})

// 문항 추가
const survey = document.querySelector('article').dataset.survey;
function makeQuestion() {
	formData = getFormData();
	
	$.ajax({
		url:'/ajax/make/question',
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify({
			question:formData[0],
			itemList:formData[1],
			survey:survey
		}), // @RequestBody로 받을 때 Unrecognized token으로 인한 JsonParseException을 피하려면 {} 형태로 보내는 data 전체에 JSON.stringify() 적용
		success:function(param) {
			if(param.result=='hasErrors') {
				const errorsKeys = Object.keys(param.errors); 
				console.log(errorsKeys.length)
				for(key of errorsKeys) {
					console.log(key + ':' + param.errors[key]);
				}
			}
			else if(param.result=='success') {
				initializeMake(true);
				console.log('추가된 문항의 PK:' + param.insertedQuestion);
				console.log('추가된 응답 범주 수:' + param.insertedItems);
			}
		} // end of success
	}); // end of Ajax
}

function getFormData() {
	const formDataQuestion = new FormData(document.querySelector('form[name="question"]'));
	const question = Object.fromEntries(formDataQuestion);
	
	const formItemList = document.querySelectorAll('form[name="item"]');
	let itemList = [];
	for(let i=0;i<formItemList.length;i++) {
		const formDataItem = new FormData(formItemList[i]);
		itemList.push(Object.fromEntries(formDataItem))
	}
	
	return [question, itemList];
}