package io.ezsurvey.web.survey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.service.survey.BookmarkSurveyCUDService;
import io.ezsurvey.service.survey.BookmarkSurveyReadService;
import io.ezsurvey.service.survey.SurveyCUDService;
import io.ezsurvey.service.survey.SurveyReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@RestController
public class SurveyCUDAjaxController {
	private final BookmarkSurveyCUDService bookmarkSurveyCUDService;
	private final BookmarkSurveyReadService bookmarkSurveyReadService;
	private final SurveyCUDService surveyCUDService;
	private final SurveyReadService surveyReadService;
	
	@PostMapping("/ajax/delete/project/{surveyId}")
	public Map<String, String> delete(@PathVariable(name = "surveyId") Long surveyId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		if(sessionUser==null) { // 로그인되어 있지 않은 경우
			map.put("result", "logout");
		}
		else if(!sessionUser.getUserId().equals(surveyReadService.getUserIdById(surveyId))) { // 로그인한 사용자와 설문조사 생성자가 불일치하는 경우
			map.put("result", "wrongAccess");
		}
		else {
			surveyCUDService.delete(surveyId);
			map.put("result", "success");
		}
		
		return map;
	}
	
	@PostMapping("/ajax/toggle/bookmark/project/{surveyId}")
	public Map<String, String> toggleBookmark(@PathVariable(name = "surveyId") Long surveyId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		if(sessionUser==null) { // 로그인되어 있지 않은 경우
			map.put("result", "logout");
		}
		else {
			Long userId = sessionUser.getUserId();
			Long bookmarkId = bookmarkSurveyReadService.getIdBySurveyIdAndUserId(surveyId, userId);
			if(bookmarkId==null) { // 기존에 즐겨찾기되어 있지 않으면 추가
				bookmarkSurveyCUDService.insert(surveyId, userId);
				map.put("result", "inserted");
			}
			else { // 기존에 즐겨찾기되어 있으면 삭제
				bookmarkSurveyCUDService.delete(bookmarkId);
				map.put("result", "deleted");
			}
		}
		
		return map;
	}
	
	@PostMapping("/ajax/delete/bookmark/project")
	public Map<String, Object> deleteBookmark(@RequestBody List<Long> bookmarkIds, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(bookmarkIds==null || bookmarkIds.size()==0) { // 아무것도 선택하지 않은 경우
			map.put("result", "null");
		}
		else {
			SessionUser sessionUser = (SessionUser)session.getAttribute("user");
			if(sessionUser==null) { // 로그인되어 있지 않은 경우
				map.put("result", "logout");
			}
			else {
				Long userId = sessionUser.getUserId();
				Long affected_rows = bookmarkSurveyCUDService.deleteByIdIn(bookmarkIds);
	
				map.put("result", "success");
				map.put("affected_rows", affected_rows);
			}
		}
		
		return map;
	}
	
	@PostMapping("/ajax/copy/project/{surveyId}")
	public Map<String, Object> copy(@PathVariable(name = "surveyId") Long surveyId, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute("user");
		
		if(sessionUser==null) {
			map.put("result", "logout");
		}
		else {
			Long cloneId = surveyCUDService.copy(surveyId, sessionUser.getUserId());
			
			map.put("result", "success");
			map.put("cloneId", cloneId);
		}
		
		return map;
	}
}
