package io.ezsurvey.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import io.ezsurvey.exception.ErrorCode;
import io.ezsurvey.exception.InvalidSurveyOwnerException;

@ControllerAdvice // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "/error";

	@ExceptionHandler(value = InvalidSurveyOwnerException.class)
	protected ModelAndView handleInvalidSurveyOwnerException(InvalidSurveyOwnerException e, HttpServletRequest request) {
		final ErrorCode errorCode = e.getErrorCode();
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("url", request.getRequestURL());
		mav.addObject("exception", e);
		mav.addObject("errorCode", errorCode);
		mav.addObject("status", HttpStatus.valueOf(errorCode.getStatus()));
		mav.setViewName(DEFAULT_ERROR_VIEW);
		
		return mav;
	}
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("url", request.getRequestURL());
		mav.addObject("exception", e);
		mav.addObject("status", response.getStatus());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		
		return mav;
	}
}
