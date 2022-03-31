package io.ezsurvey.web.question;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ezsurvey.dto.question.RequestDTOWrapper;
import io.ezsurvey.repository.EnumMapper;

@RestController
public class QuestionCUDAjaxController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionCUDAjaxController.class);
	private static final ResourceBundle resource = ResourceBundle.getBundle("messages.validation");
	private final EnumMapper enumMapper; // AppConfig에 등록
	
	// 생성자 방식 의존성 주입
	public QuestionCUDAjaxController(EnumMapper enumMapper) {
		this.enumMapper = enumMapper;
	}
	
	@PostMapping("/ajax/make/question")
	public Map<String, Object> make(@Valid @RequestBody RequestDTOWrapper wrapper, BindingResult result, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		if(result.getAllErrors()!=null) { // 유효성 검증 결과 오류가 있으면
			Map<String, String> errors = new HashMap<>();
			
			for(ObjectError oe : result.getAllErrors()) {
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
				}
				
				errors.put(codes[0], errorMessage);
			}
			
			map.put("errors", errors); // 프론트에서 필드별 오류 메시지를 처리하기 위해 저장
			map.put("result", "hasErrors");
		}
		else {
			map.put("result", "success");
		}
		
		return map;
	}
}
