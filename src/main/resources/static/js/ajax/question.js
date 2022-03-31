const forms = document.querySelectorAll('form');
for(let i=0;i<forms.length;i++) {
	forms[i].onsubmit = function(event) {
		event.preventDefault();
	}
}

function getFormData() {
	const formDataQuestion = new FormData(document.querySelector('form[name="question"]'));
	const question = Object.fromEntries(formDataQuestion);
	
	let itemList = [];
	if(forms.length>1) { // 서버로 보낼 응답 범주가 있는 경우
		for(let i=1;i<forms.length;i++) {
			const formDataItem = new FormData(forms[i]);
			itemList.push(Object.fromEntries(formDataItem))
		}
	}
	
	return [question, itemList];
}

function makeQuestion() {
	formData = getFormData();
	
	$.ajax({
		url:'/ajax/make/question',
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify({
			question:formData[0],
			itemList:formData[1]
		}), // @RequestBody로 받을 때 Unrecognized token으로 인한 JsonParseException을 피하려면 {} 형태로 보내는 data 전체에 JSON.stringify() 적용
		success:function(param) {
			console.log(param.result)
			if(param.result=='hasErrors') {
				const errorsKeys = Object.keys(param.errors); 
				console.log(errorsKeys.length)
				for(key of errorsKeys) {
					console.log(key + ':' + param.errors[key]);
				}
			}
		} // end of success
	}); // end of Ajax
}