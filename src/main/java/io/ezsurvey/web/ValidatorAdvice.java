package io.ezsurvey.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Controller advice that adds the {@link CollectionValidator} to the 
 * {@link WebDataBinder}.
 * 
 * @author DISID CORPORATION S.L. (www.disid.com)
 */
@ControllerAdvice
public class ValidatorAdvice {
	@Autowired
	protected LocalValidatorFactoryBean validator;

	/**
	 * Adds the {@link CollectionValidator} to the supplied 
	 * {@link WebDataBinder}
	 * 
	 * @param binder web data binder.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if(binder.getTarget() instanceof Collection) { // Invalid target for Validator로 인한 IllegalStateException을 피하려면 검증 대상이 Collection인 경우에만 CollectionValidator를 적용해야 함
			binder.addValidators(new CollectionValidator(validator));
		}
	}
}
