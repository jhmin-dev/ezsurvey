// Ajax로만 서버에 전송할 것이므로 모든 form에 submit 기본 이벤트 제거
document.addEventListener('submit', function(e) {
	e.preventDefault();
})

// 결과 메시지 관련 태그들
const postResult = document.querySelector('.post-result');
const postResultDiv = postResult.querySelector('.post-result div');
const postResultIcon = postResult.querySelector('i.bi');
const postResultText = postResult.querySelector('span');

// 문항을 추가하는 함수
const survey = document.querySelector('article').dataset.survey;
function makeQuestion() {
	formData = getFormData();
	
	$.ajax({
		url:'/ajax' + current_url,
		contentType:'application/json;charset=UTF-8', // Ajax로 리스트를 넘기기 위한 옵션
		data:JSON.stringify({
			question:formData[0],
			itemList:formData[1]
		}), // @RequestBody로 받을 때 Unrecognized token으로 인한 JsonParseException을 피하려면 {} 형태로 보내는 data 전체에 JSON.stringify() 적용
		success:function(param) {
			postResultText.textContent = ''; // 기존 메시지 제거
			
			if(param.result=='hasErrors') { // 문항 추가에 실패한 경우
				postResultDiv.className = 'failure'; // 배경색 변경
				
				postResultIcon.classList.remove('bi-check-circle-fill');
				postResultIcon.classList.add('bi-exclamation-triangle-fill');
				
				const errorsKeys = Object.keys(param.errors);
				let i=0, messages = '';
				for(key of errorsKeys) {
					if(i!=0) messages += '\n'; // 오류 메시지 2건 이상인 경우 줄바꿈 처리
					messages += param.errors[key];
					i++;
				}
				postResultText.textContent = messages;	
			}
			else if(param.result=='success') { // 문항 추가에 성공한 경우
				initializeMake(true); // form 태그들 초기화
				
				postResultDiv.className = 'success'; // 배경색 변경
				
				postResultIcon.classList.remove('bi-exclamation-triangle-fill');
				postResultIcon.classList.add('bi-check-circle-fill');
				
				postResultText.textContent = '성공적으로 문항을 추가했습니다.'
			}
			
			if(param.result=='hasErrors' || param.result=='success') { // 정상적 응답인 경우
				postResult.classList.remove('display-none'); // 결과 메시지 노출
			}
			else { // 비정상적 응답인 경우
				postResult.classList.add('display-none'); // 결과 메시지 숨김
				alert('문항 추가시 오류가 발생했습니다!');
			}
		}, // end of success
		error:function() { // Global Handler 이전에 수행됨
			postResult.classList.add('display-none'); // 결과 메시지 숨김
		}
	}); // end of Ajax
}

// form 태그들을 FormData 객체로 변환하는 함수
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

// 수정할 문항을 불러오는 함수
function getQuestionToEdit() {
	$.ajax({
		url:'/ajax' + current_url,
		type:'GET',
		success:function(param) {
			console.log(param);
		}
	}); // end of ajax
}

// 문항을 삭제하는 함수
function deleteQuestion() {
	const project_url = current_url.slice(0,current_url.indexOf('/edit/question'));
	const question_url = current_url.replace(project_url,'').replace('edit','delete');
	
	$.ajax({
		url:'/ajax' + question_url,
		success:function(param) {
			if(param.result=='success') {
				alert('문항이 삭제되었습니다!');
				location.replace(project_url + '/index');
			}
			else {
				postResultDiv.className = 'failure'; // 배경색 변경
				
				postResultIcon.classList.remove('bi-check-circle-fill');
				postResultIcon.classList.add('bi-exclamation-triangle-fill');
				
				postResultText.textContent = '문항을 삭제하는 데 실패했습니다.'
			}
		}, // end of success
		error:function() { // Global Handler 이전에 수행됨
			postResult.classList.add('display-none'); // 결과 메시지 숨김
		}
	}); // end of Ajax
}