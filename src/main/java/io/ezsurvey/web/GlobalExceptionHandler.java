package io.ezsurvey.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import io.ezsurvey.exception.BusinessException;
import io.ezsurvey.exception.DeletedSurveyException;
import io.ezsurvey.exception.EntityNotFoundException;
import io.ezsurvey.exception.ErrorCode;
import io.ezsurvey.exception.ErrorPage;
import io.ezsurvey.exception.InvalidSurveyOwnerException;
import io.ezsurvey.exception.InvalidSurveyStatusException;
import io.ezsurvey.exception.InvalidSurveyVisibilityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
	@ExceptionHandler(value = EntityNotFoundException.class)
	public ModelAndView handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
		return getModelAndViewFromBusinessException(e, request);
	}
	
	@ExceptionHandler(value = DeletedSurveyException.class)
	public ModelAndView handleDeletedSurveyException(DeletedSurveyException e, HttpServletRequest request) {
		return getModelAndViewFromBusinessException(e, request);
	}
	
	@ExceptionHandler(value = InvalidSurveyVisibilityException.class)
	public ModelAndView handleInvalidSurveyVisibilityException(InvalidSurveyVisibilityException e, HttpServletRequest request) {
		return getModelAndViewFromBusinessException(e, request);
	}
	
	@ExceptionHandler(value = InvalidSurveyOwnerException.class)
	public ModelAndView handleInvalidSurveyOwnerException(InvalidSurveyOwnerException e, HttpServletRequest request) {
		return getModelAndViewFromBusinessException(e, request);
	}
	
	@ExceptionHandler(value = InvalidSurveyStatusException.class)
	public ModelAndView handleInvalidSurveyStatusException(InvalidSurveyStatusException e, HttpServletRequest request) {
		return getModelAndViewFromBusinessException(e, request);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		log.error("defaultErrorHandler", e);
		
		// If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("url", request.getRequestURL());
		mav.addObject("exception", e);
		mav.addObject("status", response.getStatus());
		mav.setViewName(ErrorPage.DEFAULT_VIEW.getName());
		
		return mav;
	}
	
	private <E extends BusinessException> ModelAndView getModelAndViewFromBusinessException(E e, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		ErrorCode errorCode = e.getErrorCode();
		
		mav.addObject("url", request.getRequestURL());
		mav.addObject("exception", e);
		mav.addObject("errorCode", errorCode);
		mav.addObject("status", errorCode.getStatus());
		
		mav.setViewName(ErrorPage.DEFAULT_VIEW.getName());
		
		return mav;
	}
}
