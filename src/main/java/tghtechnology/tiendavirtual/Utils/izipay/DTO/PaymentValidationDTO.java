package tghtechnology.tiendavirtual.Utils.izipay.DTO;

import lombok.Getter;
import lombok.Setter;

public class PaymentValidationDTO {

	private final String successMessage = "El hash se validó correctamente.";
	private final String failMessage = "El hash de validación es incorrecto.";
	
	@Getter @Setter
	private Boolean success;
	@Getter @Setter
	private String message;
	
	public PaymentValidationDTO(Boolean success) {
		this.success = success;
		this.message = success ? successMessage : failMessage;
	}
	
}
