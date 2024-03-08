package tghtechnology.chozaazul.Utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;

import jakarta.validation.Validation;
import lombok.NoArgsConstructor;
import tghtechnology.chozaazul.Utils.Exceptions.CustomValidationFailedException;

@Component
@NoArgsConstructor
public class CustomBeanValidator {

	private jakarta.validation.Validator jakartaValidator = Validation.buildDefaultValidatorFactory().getValidator();
	private org.springframework.validation.Validator springValidator = new SpringValidatorAdapter(jakartaValidator);
	
	public <T> void validar(T object) throws CustomValidationFailedException {
		
		WebDataBinder dataBinder = new WebDataBinder(object);
		dataBinder.setValidator(springValidator);
		dataBinder.validate();
		BindingResult result = dataBinder.getBindingResult();
		
		if(!result.getAllErrors().isEmpty()) {
			throw new CustomValidationFailedException(result);
		}
	}
	
}
