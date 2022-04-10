// summernote 편집기를 DOM 완성 전에 초기화하면 편집기의 기능이 동작하지 않으므로 주의
$(function() {
	initializeSummernote();
});

// summernote 편집기를 초기화하는 함수
function initializeSummernote() {
	$('.summernote').summernote({
		toolbar: [
			// [groupName, [list of button]]
			['style', ['bold', 'italic', 'underline', 'clear']],
			['fontsize', ['fontsize']],
			['color', ['forecolor', 'backcolor']],
			['para', ['ul', 'ol', 'paragraph']],
			['font', ['superscript', 'subscript']],
			['embed', ['link']],
			['misc', ['undo', 'redo']]
		],
		popover: {
			link: [
				['link', ['linkDialogShow', 'unlink']]
			]
		},
		lang: 'ko-KR',
		inheritPlaceholder: true
	}); // end of summernote()	
}

// sumbit 이벤트
for(const f of document.querySelectorAll('form')) {
	f.addEventListener('submit', function(e) {
		trimSummernote();
		
		// 기본 이벤트를 제거하면 Spring의 form:form 태그와 연동된 에러 메시지 UI를 수동으로 처리해야 함
		// e.preventDefault();
	});
}

// summernote 편집기에서 불필요한 공백을 제거하는 함수
function trimSummernote() {
	// 현재 summernote 편집기로 작성된 내용 가져오기
	let markupStr = $('.summernote').summernote('code');
	
	// 작성된 내용에서 불필요한 <p><br></p>를 제거
	while(markupStr.startsWith('<p><br></p>')){
		markupStr=markupStr.replace('<p><br></p>','');
	}
	while(markupStr.endsWith('<p><br></p>')){
		markupStr=markupStr.replace(new RegExp('<p><br></p>$'),'');
	}
	
	// summernote 편집기의 작성 중인 내용을 <p><br></p>가 제거된 문자열로 교체
	$('.summernote').summernote('code', markupStr);
}