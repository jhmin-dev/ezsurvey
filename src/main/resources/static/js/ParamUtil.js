// 현재 URL의 GET 파라미터 목록
const params = new URLSearchParams(location.search);

// 현재 URL의 GET 파라미터 목록에서 특정 key, value의 쌍을 제거하는 함수
URLSearchParams.prototype.remove = function(key, value) {
    const entries = this.getAll(key);
    const newEntries = entries.filter(entry => entry !== value);
    this.delete(key);
    newEntries.forEach(newEntry => this.append(key, newEntry));
}

// 변경된 GET 파라미터를 적용하는 함수
function applyParameters() {
	location.href = location.pathname + '?' + decodeURIComponent(params);
}

// GET 파라미터를 제거하는 함수
function clearParameters() {
	location.href = location.pathname;
}