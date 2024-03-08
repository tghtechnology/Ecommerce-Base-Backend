package tghtechnology.chozaazul.Utils.Exceptions;

import org.springframework.validation.BindingResult;

import lombok.Getter;

public class CustomValidationFailedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private BindingResult result;
	
	public CustomValidationFailedException(BindingResult result) {
		this.result = result;
	}
	
}
