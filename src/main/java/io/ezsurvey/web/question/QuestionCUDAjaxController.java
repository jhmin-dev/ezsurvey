package io.ezsurvey.web.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.question.ItemRequestDTO;
import io.ezsurvey.dto.question.QuestionRequestDTO;
import io.ezsurvey.dto.question.QuestionServiceDTO;
import io.ezsurvey.dto.question.RequestDTOWrapper;
import io.ezsurvey.repository.EnumMapper;
import io.ezsurvey.service.question.ItemReadService;
import io.ezsurvey.service.question.QuestionCUDService;
import io.ezsurvey.service.question.QuestionReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 방식 의존성 주입
@RestController
public class QuestionCUDAjaxController {
	private static final ResourceBundle resource = ResourceBundle.getBundle("messages.validation");
	private final EnumMapper enumMapper; // AppConfig에 등록
	private final QuestionCUDService questionCUDService;
	private final QuestionReadService questionReadService;
	private final ItemReadService itemReadService;
	
	@PostMapping("/ajax/edit/project/{surveyId}/make/question")
	public Map<String, Object> make(@PathVariable(name = "surveyId") Long surveyId
			, @Valid @RequestBody RequestDTOWrapper wrapper, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<>();
		
		if(bindingResult.getAllErrors().size()>0) { // 유효성 검증 결과 오류가 있으면
			Map<String, String> errors = new HashMap<>();
			
			for(ObjectError oe : bindingResult.getAllErrors()) {
				String[] codes = oe.getCodes(); // 현재 에러에 대응하는 오류 코드들
				String errorMessage = null;
				
				// validation.properties에 오류 코드가 정의되어 있으면 해당 메시지를 사용하고, 그렇지 않으면 Spring의 기본 메시지를 사용
				try {
					errorMessage = resource.getString(codes[0]);
				}
				catch(MissingResourceException mre) { // 응답 범주에서 발생한 오류의 경우 codes[0]은 인덱스를 포함하고, codes[1]은 포함하지 않음
					try {
						errorMessage = resource.getString(codes[1]);
					}
					catch(MissingResourceException mre2) {
						errorMessage = oe.getDefaultMessage();
					}
				} // end of try~catch
				
				errors.put(codes[0], errorMessage);
			} // end of for
			
			map.put("errors", errors); // 프론트에서 필드별 오류 메시지를 처리하기 위해 저장
			map.put("result", "hasErrors");
		}
		else {
			// 문항 추가
			Map<String, Object> inserted = questionCUDService.insert(wrapper.getQuestion().toServiceDTO()
					, wrapper.getItemList().stream().map(ItemRequestDTO::toServiceDTO).collect(Collectors.toList())
					, surveyId);
			
			map.put("result", "success");
			map.put("insertedQuestion", inserted.get("question"));
			map.put("insertedItems", inserted.get("items"));
		}
		
		return map;
	}
	
	@GetMapping("/ajax/edit/project/{surveyId}/edit/question/{questionId}")
	public RequestDTOWrapper edit(@PathVariable(name = "surveyId") Long surveyId
			, @PathVariable(name = "questionId") Long questionId) {
		QuestionServiceDTO questionServiceDTO = questionReadService.getServiceDTOById(questionId);
		QuestionRequestDTO question = new QuestionRequestDTO(questionServiceDTO);
		
		if(questionServiceDTO.getItems()>0) { // 응답 범주가 있는 문항(선다형, 척도형 등)	
			List<ItemRequestDTO> itemList = itemReadService.findServiceDTOByQuestionId(questionId)
					.stream().map(ItemRequestDTO::new).collect(Collectors.toList());
			
			return new RequestDTOWrapper(question, itemList);
		}
		else { // 응답 범주가 없는 문항(단답형 등)
			return new RequestDTOWrapper(question, null);
		}
	}
	
	@PostMapping("/ajax/edit/project/{surveyId}/edit/question/{questionId}")
	public Map<String, Object> edit(@PathVariable(name = "surveyId") Long surveyId
			, @PathVariable(name = "questionId") Long questionId
			, @Valid @RequestBody RequestDTOWrapper wrapper, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<>();
		
		return map;
	}
	
	@PostMapping("/ajax/delete/question/{questionId}")
	public Map<String, Object> delete(@PathVariable(name = "questionId") Long questionId) {
		Map<String, Object> map = new HashMap<>();
		
		questionCUDService.delete(questionId);
		
		map.put("result", "success");
		
		return map;
	}
}
