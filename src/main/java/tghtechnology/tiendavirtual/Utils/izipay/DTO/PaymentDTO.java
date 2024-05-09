package tghtechnology.tiendavirtual.Utils.izipay.DTO;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.IzipayResponse;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.Answers.FormToken;

@Getter
@Setter
public class PaymentDTO {

	private String formToken;
	private String uid_pedido;
	
	public PaymentDTO(IzipayResponse<FormToken> iziResponse, UUID uid) {
		this.formToken = iziResponse.getAnswer().getFormToken();
		this.uid_pedido = uid.toString().replace("-", "_");
	}
	
}
