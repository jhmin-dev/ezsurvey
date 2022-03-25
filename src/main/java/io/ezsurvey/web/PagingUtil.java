package io.ezsurvey.web;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class PagingUtil {
	public static <T> void setPageAttributes(Model model, Page<T> page, int pageBlock) {	
		long totalElements = page.getTotalElements();
		model.addAttribute("totalElements", totalElements);
		
		if(totalElements==0) return; // 검색 결과가 없으면 페이지 블럭을 계산하지 않음
		
		int pageNumber = page.getPageable().getPageNumber(); // 요청 파라미터상 현재 페이지 번호-1
		int totalPages = page.getTotalPages(); // 요청 파라미터상 마지막 페이지와 같은 값
		int startPageBlock = (pageNumber/pageBlock)*pageBlock+1; // 1~5페이지는 start 1이어야 하므로 (0~4)/5+1이 올바른 계산식
		int endPageBlock = Math.min(startPageBlock+pageBlock-1, totalPages); // 첫 번째 인자는 pageBlock의 배수, 두 번째 인자는 요청 파라미터상 마지막 페이지

		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPageBlock", startPageBlock);
		model.addAttribute("endPageBlock", endPageBlock);
	}
}
