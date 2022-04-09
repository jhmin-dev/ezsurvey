package io.ezsurvey.web.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.question.QuestionResponseDTO;
import io.ezsurvey.repository.EnumMapper;
import io.ezsurvey.service.question.QuestionReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@RestController
public class QuestionReadAjaxController {
	private final QuestionReadService questionReadService;
	private final EnumMapper enumMapper; // AppConfig에 등록
	
	@PostMapping("/ajax/edit/project/{surveyId}/index")
	public Map<String, Object> index(@PathVariable(name = "surveyId") Long surveyId, Long lastQuestionId, Integer totalElements) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int pageSize = 10;
		List<QuestionResponseDTO> list = questionReadService.findPaginationDTOBySurveyId(surveyId, lastQuestionId, pageSize)
				.stream().map(QuestionResponseDTO::new).collect(Collectors.toList());
		
		// 더보기 가능 여부 저장
		boolean hasMore = false;
		if(list.size()>pageSize) { // 반환된 목록이 지정한 페이지 크기보다 큰 경우(메서드는 pageSize+1건만큼 조회하여 반환)
			hasMore = true;
			list.remove(pageSize); // 추가로 읽어 온 마지막 요소를 제거
		}
		map.put("list", list);
		map.put("hasMore", hasMore);
		
		// 마지막 문항 번호 저장
		if(list.size()>0) { // 목록이 비어 있지 않으면
			Long newLastQuestionId = list.get(list.size()-1).getQuestionId();
			map.put("last", newLastQuestionId);
		}
		
		map.put("category", enumMapper.get("Category"));
		map.put("result", "success");
		
		return map;
	}
}
