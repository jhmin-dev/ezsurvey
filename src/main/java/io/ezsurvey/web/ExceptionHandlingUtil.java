package io.ezsurvey.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import io.ezsurvey.exception.BusinessException;
import io.ezsurvey.exception.ErrorCode;
import io.ezsurvey.exception.ErrorPage;

public class ExceptionHandlingUtil {
	public static <E extends BusinessException> ModelAndView getModelAndView(E e, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		ErrorCode errorCode = e.getErrorCode();
		
		mav.addObject("url", request.getRequestURL());
		mav.addObject("exception", e);
		mav.addObject("errorCode", errorCode);
		mav.addObject("status", HttpStatus.valueOf(errorCode.getStatus()));
		
		mav.setViewName(ErrorPage.DEFAULT_VIEW.getName());
		
		return mav;
	}	
}
