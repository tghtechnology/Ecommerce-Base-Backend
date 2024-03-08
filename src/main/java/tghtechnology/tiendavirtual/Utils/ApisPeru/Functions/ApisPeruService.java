package tghtechnology.tiendavirtual.Utils.ApisPeru.Functions;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruError;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;

/**
 * Servicio que se conecta a ApisPeru para realizar las solicitudes de venta. Generando tanto boleta como factura
 */
@Component
@NoArgsConstructor
public class ApisPeruService {

	@Value("${apisperu.url}")
	private String apisPeruUrl;
	@Value("${apisperu.token}")
	private String apisPeruToken;
	
	/**
	 * Envía un objeto de tipo {@value Boleta} a ApisPeru para su procesamiento por SUNAT.
	 * @param boleta La boleta a procesar.
	 * @return Respuesta de la SUNAT en formato {@value ApisPeruResponse} en caso no haya problemas de validación.
	 * @throws IOException Al encontrar un fallo traduciendo la respuesta de la API del formato JSON a ApisPeruResponse.
	 * @throws ApisPeruResponseException Al encontrar un fallo de validación en la boleta.
	 */
	public ApisPeruResponse enviarBoleta(Boleta boleta) throws IOException, ApisPeruResponseException {
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(apisPeruToken);
		
		try {
			
			HttpEntity<Boleta> entity = new HttpEntity<>(boleta, headers);
			
		String result = rt.postForObject(apisPeruUrl + "/invoice/send", entity ,String.class);
		return new ObjectMapper().readValue(result, ApisPeruResponse.class);
		//return result;
		} catch(HttpStatusCodeException ex) {
			HttpStatusCode status = ex.getStatusCode();
			ApisPeruError errResponse = ex.getResponseBodyAs(ApisPeruError.class);
			throw new ApisPeruResponseException(status, errResponse);
		}
		
		
	}
	
	/**
	 * Envía un objeto de tipo {@value Boleta} a ApisPeru para obtener un comprobante de venta en formato PDF.
	 * @param boleta La boleta a procesar.
	 * @return Comprobante de venta en formato PDF.
	 * @throws ApisPeruResponseException Al encontrar un fallo de validación en la boleta.
	 */
	public byte[] enviarBoletaPdf(Boleta bol) throws ApisPeruResponseException {
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(apisPeruToken);
		
		try {
			
			HttpEntity<Boleta> entity = new HttpEntity<>(bol, headers);
			
		byte[] result = rt.postForObject(apisPeruUrl + "/invoice/pdf", entity ,byte[].class);
		return result;
		//return result;
		} catch(HttpStatusCodeException ex) {
			HttpStatusCode status = ex.getStatusCode();
			ApisPeruError errResponse = ex.getResponseBodyAs(ApisPeruError.class);
			throw new ApisPeruResponseException(status, errResponse);
		}
		
		
	}
	
	
}
