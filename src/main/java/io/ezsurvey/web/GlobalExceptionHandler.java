package io.ezsurvey.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import io.ezsurvey.exception.DeletedSurveyException;
import io.ezsurvey.exception.EntityNotFoundException;
import io.ezsurvey.exception.ErrorPage;
import io.ezsurvey.exception.InvalidSurveyOwnerException;
import io.ezsurvey.exception.InvalidSurveyStatusException;
import io.ezsurvey.exception.InvalidSurveyVisibilityException;

@ControllerAdvice // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = EntityNotFoundException.class)
	public ModelAndView handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
		logger.error("handleEntityNotFoundException", e);
		
		return ExceptionHandlingUtil.getModelAndView(e, request);
	}
	
	@ExceptionHandler(value = DeletedSurveyException.class)
	public ModelAndView handleDeletedSurveyException(DeletedSurveyException e, HttpServletRequest request) {
		logger.error("handleDeletedSurveyException", e);
		
		return ExceptionHandlingUtil.getModelAndView(e, request);
	}
	
	@ExceptionHandler(value = InvalidSurveyVisibilityException.class)
	public ModelAndView handleInvalidSurveyVisibilityException(InvalidSurveyVisibilityException e, HttpServletRequest request) {
		logger.error("handleInvalidSurveyVisibilityException", e);
		
		return ExceptionHandlingUtil.getModelAndView(e, request);
	}
	
	@ExceptionHandler(value = InvalidSurveyOwnerException.class)
	public ModelAndView handleInvalidSurveyOwnerException(InvalidSurveyOwnerException e, HttpServletRequest request) {
		logger.error("handleInvalidSurveyOwnerException", e);
		
		return ExceptionHandlingUtil.getModelAndView(e, request);
	}
	
	@ExceptionHandler(value = InvalidSurveyStatusException.class)
	public ModelAndView handleInvalidSurveyStatusException(InvalidSurveyStatusException e, HttpServletRequest request) {
		logger.error("handleInvalidSurveyStatusException", e);
		
		return ExceptionHandlingUtil.getModelAndView(e, request);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		logger.error("defaultErrorHandler", e);
		
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
}
