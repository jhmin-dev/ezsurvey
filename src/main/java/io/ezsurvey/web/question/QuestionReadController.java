package io.ezsurvey.web.question;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.repository.EnumMapper;
import io.ezsurvey.web.PagingUtil;

@Controller
public class QuestionReadController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionReadController.class);
	
	@Autowired
	private EnumMapper enumMapper; // AppConfig에 등록
	
	@Autowired @Qualifier("searchFieldQuestion")
	private List<EnumDTO> searchField;
	
	// BookmarkQuestion에서 검색하기 때문에 sort 기준 프로퍼티명은 question. 또는 user.으로 접근해야 함
	@RequestMapping("/bookmark/question")
	public String bookmark(@PageableDefault(page = 0, sort = "question.id", direction = Direction.DESC) Pageable pageable
			, String field, String word, Model model, HttpSession session) {
		// 세션에 저장된 회원 정보 구하기
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		// 로그인한 사용자가 즐겨찾기한 문항 중 전체 공개인 문항 목록 가져오기
		Page<Object> page = Page.empty();
		
		
		// 문항 목록 관련 정보 저장
		setQuestionAttributes(model, page);
		
		// 현재 요청 URL 관련 정보 저장
		model.addAttribute("title", "즐겨찾기: 문항");
		model.addAttribute("link", "bookmark");
		
		return "/list"; // Tiles 설정명 반환
	}
	
	public void setQuestionAttributes(Model model, Page<Object> page) {
		model.addAttribute("page", page);
		PagingUtil.setPageAttributes(model, page, 5);
		model.addAttribute("searchField", searchField);
		model.addAttribute("type", "question");
		model.addAttribute("category", enumMapper.get("Category")); // 문항 목록 화면 구성에 필요한 정보 저장
	}
}
