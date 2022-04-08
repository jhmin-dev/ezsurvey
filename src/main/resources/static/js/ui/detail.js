const dates = document.querySelectorAll('.date');
for(const date of dates) {
	if(date.dataset.date) {
		date.textContent = addDateUnitsTo(date.dataset.date);
		date.parentNode.title = date.dataset.date.split('.')[0].replace('T',' ');
	}
	else {
		date.classList.add('notice');
		const word = date.parentNode.querySelector('label').textContent;
		date.textContent = '이 설문조사는 '+ word + getPostposition(word,'을/를') +  ' 아직 지정하지 않았습니다.';
	}
}

const preview = document.querySelector('.preview-link');
preview.href = location.origin + location.pathname + '/preview';
preview.textContent = location.origin + location.pathname + '/preview';
if(preview.dataset.shared && preview.dataset.visibility == 'link_only') {
	preview.href += '/' + preview.dataset.shared;
	preview.textContent += '/' + preview.dataset.shared;
}
