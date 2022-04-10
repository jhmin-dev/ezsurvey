for(const d of document.querySelectorAll('[data-date]')) {
	date = d.dataset.date;
	d.textContent = addDateUnitsTo(date);
	d.title = date.split('.')[0].replace('T',' ');
}