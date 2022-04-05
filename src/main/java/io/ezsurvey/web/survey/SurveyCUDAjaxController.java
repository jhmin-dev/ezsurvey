package io.ezsurvey.web.survey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.service.survey.BookmarkSurveyCUDService;
import io.ezsurvey.service.survey.SurveyCUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@RestController
public class SurveyCUDAjaxController {
	private final BookmarkSurveyCUDService bookmarkSurveyCUDService;
	private final SurveyCUDService surveyCUDService;
	
	@PostMapping("/ajax/delete/project")
	public Map<String, String> delete(Long survey, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		if(sessionUser==null) { // 로그인되어 있지 않은 경우
			map.put("result", "logout");
		}
		else if(sessionUser.getUserId()!=surveyCUDService.getRequestDTOById(survey).getUser().getId()) { // 로그인한 사용자와 설문조사 생성자가 불일치하는 경우
			map.put("result", "wrongAccess");
		}
		else {
			surveyCUDService.delete(survey);
			map.put("result", "success");
		}
		
		return map;
	}
	
	@PostMapping("/ajax/toggle/bookmark/project/{survey}")
	public Map<String, String> toggleBookmark(@PathVariable(name = "survey") Long survey, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		if(sessionUser==null) { // 로그인되어 있지 않은 경우
			map.put("result", "logout");
		}
		else {
			Long member = sessionUser.getUserId();
			Long bookmark = bookmarkSurveyCUDService.getBookmark(survey, member);
			if(bookmark==null) { // 기존에 즐겨찾기되어 있지 않으면 추가
				bookmarkSurveyCUDService.insertBookmark(survey, member);
				map.put("result", "inserted");
			}
			else { // 기존에 즐겨찾기되어 있으면 삭제
				bookmarkSurveyCUDService.deleteBookmark(bookmark, member);
				map.put("result", "deleted");
			}
		}
		
		return map;
	}
	
	@PostMapping("/ajax/delete/bookmark/project")
	public Map<String, Object> deleteBookmark(@RequestBody List<Long> bookmarks, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(bookmarks==null || bookmarks.size()==0) { // 아무것도 선택하지 않은 경우
			map.put("result", "null");
		}
		else {
			SessionUser sessionUser = (SessionUser)session.getAttribute("user");
			if(sessionUser==null) { // 로그인되어 있지 않은 경우
				map.put("result", "logout");
			}
			else {
				Long member = sessionUser.getUserId();
				Long affected_rows = bookmarkSurveyCUDService.deleteBookmarksByIdIn(bookmarks, member);
	
				map.put("result", "success");
				map.put("affected_rows", affected_rows);
			}
		}
		
		return map;
	}
}
