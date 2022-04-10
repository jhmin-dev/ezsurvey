const profileButton = document.querySelector('.icon-menu img.profile');
const dropdownMenu = document.querySelector('nav.dropdown-menu');
const hamburgerButton = document.querySelector('header .hamburger');
const hamburgerMenu = document.querySelector('nav.hamburger-menu');
const headerContainer = document.querySelector('.header-container');

// 드롭다운 메뉴를 토글하는 이벤트
document.addEventListener('click', function(e) {
	if(e.target === profileButton) { // 프로필 버튼을 클릭하면
		dropdownMenu.classList.toggle('display-none'); // 드롭다운 메뉴 토글
	}
	else if(!dropdownMenu.contains(e.target)) { // 프로필 버튼과 드롭다운 메뉴 외의 영역을 클릭하면
		dropdownMenu.classList.add('display-none'); // 드롭다운 메뉴 숨김
	}
});

// 햄버거 메뉴를 토글하는 이벤트
document.addEventListener('click', function(e) {
	if(hamburgerButton.contains(e.target)) { // 햄버거 메뉴 버튼을 클릭하면
		if(hamburgerButton.querySelector('.hamburger-button').classList.contains('clicked')) {
			setHamburgerOff(300);
		}
		else {
			setHamburgerOn(300);
		}	
	}
	else if(!hamburgerMenu.contains(e.target) && !hamburgerMenu.classList.contains('display-none')) { // 햄버거 메뉴 버튼과 햄버거 메뉴 외의 영역을 클릭하면
		setHamburgerOff(300);
	}
});

function setHamburgerOn(time) {
	hamburgerButton.querySelector('.hamburger-button').classList.add('clicked');
	setTimeout(() => {
		hamburgerMenu.querySelector('li.not-link').append(hamburgerButton);
		hamburgerMenu.classList.remove('display-none');
	}, time);
}

function setHamburgerOff(time) {
	hamburgerButton.querySelector('.hamburger-button').classList.remove('clicked');
	setTimeout(() => {
		headerContainer.append(hamburgerButton);
		hamburgerMenu.classList.add('display-none');
	}, time);
}

// 드롭다운/햄버거 메뉴를 화면 크기에 따라 숨기는 이벤트; documentElement나 body의 offset, scroll, client 너비는 CSS 미디어 쿼리와 다르게 동작함
window.addEventListener('resize', function() {
	if(window.matchMedia('(max-width:992px)').matches) { // 너비 992px 이하에서는
		dropdownMenu.classList.add('display-none'); // 드롭다운 메뉴가 열려져 있는 경우 숨김
	}
	else if(window.matchMedia('(min-width:993px)').matches) { // 너비 993px 이상에서는
		setHamburgerOff(0);
	}
});