package tghtechnology.tiendavirtual.Utils.Validation.Validators;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tghtechnology.tiendavirtual.Utils.Validation.Constraints.InDateRange;

public class DateValidator implements ConstraintValidator<InDateRange, LocalDate>{

    private LocalDate before;
    private LocalDate after;

    @Override
    public void initialize(InDateRange constraintAnnotation) {
    	before = LocalDate.parse(constraintAnnotation.before());
        after = LocalDate.parse(constraintAnnotation.after());
        
        if(after.isAfter(before))
        	throw new IllegalArgumentException("AFTER field cannot be after the BEFORE field.");
    }
	
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		return value == null || (value.isAfter(after) && value.isBefore(before));
	}

}
