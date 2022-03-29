package io.ezsurvey.web.question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import io.ezsurvey.repository.EnumMapper;

@Controller
public class QuestionCUDController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionCUDController.class);
	
	@Autowired
	private EnumMapper enumMapper; // AppConfig에 등록
	
}
