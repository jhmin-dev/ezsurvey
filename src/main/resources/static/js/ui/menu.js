const profile_button = document.querySelector('.icon-menu img.profile');
const dropdown_menu = document.querySelector('nav.dropdown-menu');
document.addEventListener('click', function(e) {
	if(e.target === profile_button) { // 프로필 버튼을 클릭하면
		dropdown_menu.classList.toggle('display-none'); // 드롭다운 메뉴 토글
	}
	else if(!dropdown_menu.contains(e.target)) { // 프로필 버튼과 드롭다운 메뉴 외의 영역을 클릭하면
		dropdown_menu.classList.add('display-none'); // 드롭다운 메뉴 숨김
	}
});