// 인자로 전달된 노드의 모든 자식 태그를 삭제하는 함수
function clearChildNodes(parents) {
	for(parent of parents) {
		while(parent.hasChildNodes()) {
			parent.removeChild(parent.lastChild);
		}
	}
}
