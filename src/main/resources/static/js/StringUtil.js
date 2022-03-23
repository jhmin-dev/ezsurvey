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