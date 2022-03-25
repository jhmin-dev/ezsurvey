function getPostposition(word, where) {
	let last = (word.charCodeAt(word.length-1) - '가'.charCodeAt(0)) % 28; // word의 마지막 글자의 종성 유니코드
	
	where_array = where.split('/');
	
	if(last>28 || last<0) { // 마지막 글자가 한글이 아닌 경우
		return where_array[0] + '(' + where_array[1] + ')';
	}
	else {
		// 받침 유무에 따라 적절한 조사 반환
		if(where_array.includes('은')) return last>0 ? '은' : '는';
		if(where_array.includes('이')) return last>0 ? '이' : '가';
		if(where_array.includes('을')) return last>0 ? '을' : '를';
		if(where_array.includes('과')) return last>0 ? '과' : '와';
		
		// ㄹ받침이 있는 경우에 유의하여 적절한 조사 반환
		if(where_array.includes('으로')) return last>0 && last!=8 ? '으로' : '로';
	}
}

function getTimeSince(date) {
	const seconds = Math.floor((new Date() - new Date(date)) / 1000);
	
	let interval = seconds / (60*60*24*365);
	if(interval>=2) return Math.floor(interval) + "년 전";
	if(interval>=1) return "작년";
	
	interval = seconds / (60*60*24*30);
	if(interval>=3) return Math.floor(interval) + "달 전";
	if(interval>=2) return "두 달 전";
	if(interval>=1) return "한 달 전";
	
	interval = seconds / (60*60*24);
	if(interval>=16) return Math.floor(interval) + "일 전";
	if(interval>=15) return "보름 전";
	if(interval>=8) return Math.floor(interval) + "일 전";
	if(interval>=7) return "일주일 전";
	if(interval>=5) return Math.floor(interval) + "일 전";
	if(interval>=4) return "나흘 전";
	if(interval>=3) return "사흘 전";
	if(interval>=2) return "이틀 전";
	if(interval>=1) return "어제";
	
	interval = seconds / (60*60);
	if(interval>=1) return Math.floor(interval) + "시간 전";
	
	interval = seconds / 60;
	if(interval>=1) return Math.floor(interval) + "분 전";		
	if(seconds<0) return "몇 초 후";
	if(seconds<5) return "몇 초 전";
	return seconds + "초 전";
}