package tghtechnology.tiendavirtual.Utils.izipay;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import tghtechnology.tiendavirtual.Services.SettingsService;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.izipay.Exceptions.IzipayResponseException;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.IzipayCharge;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.IzipayError;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.IzipayResponse;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.Answers.FormToken;

@Slf4j
@Getter
@Service
@AllArgsConstructor
public class IzipayService {

	private SettingsService settings;
	
	public IzipayResponse<FormToken> createPayment(IzipayCharge charge) throws IzipayResponseException, JsonMappingException, JsonProcessingException{
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(Base64.encode(String.format("%s:%s", settings.getString("izipay.username"), settings.getString("izipay.password"))).toString());
		try {
			
			HttpEntity<IzipayCharge> request = new HttpEntity<>(charge, headers);
			String response = rt.postForObject(settings.getString("izipay.route") + "/Charge/CreatePayment", request, String.class);
			//return new ObjectMapper().readValue(response, new TypeReference<Map<String, Object>>() {});
			IzipayResponse<?> mappedResponse = new ObjectMapper().readValue(response, new TypeReference<IzipayResponse<?>>() {});
			
			if(mappedResponse.getStatus().equals("ERROR")) {
				IzipayResponse<IzipayError> errorResponse = new ObjectMapper().readValue(response, new TypeReference<IzipayResponse<IzipayError>>() {});
				IzipayError error = errorResponse.getAnswer();
				throw new IzipayResponseException(HttpStatus.INTERNAL_SERVER_ERROR, error);
			} else {
				return new ObjectMapper().readValue(response, new TypeReference<IzipayResponse<FormToken>>() {});
			}
			
		} catch (HttpStatusCodeException ex) {
			HttpStatusCode status = ex.getStatusCode();
			log.error(ex.getMessage());
			throw new IzipayResponseException(status, null);
		}
	}
	
	public Boolean validatePayment(Map<String, Object> body) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
		
		Object answer = body.get("clientAnswer");
		String hash = (String) body.get("hash");
		
		if(answer == null) throw new DataMismatchException("clientAnswer", "No puede ser nulo");
		if(hash == null) throw new DataMismatchException("hash", "No puede ser nulo");
		
		// Validar el hash
		ObjectMapper mapper = new ObjectMapper();
		String clientAnswer = mapper.writeValueAsString(answer);
		
		final String algorithm = "HmacSHA256";
		Mac sha256_HMAC = Mac.getInstance(algorithm);
		SecretKeySpec secretKey = new SecretKeySpec(settings.getString("izipay.mac_sha256key").getBytes(), algorithm);
		sha256_HMAC.init(secretKey);
		
		String answerHash = IzipayUtils.bytesToHex(sha256_HMAC.doFinal(clientAnswer.getBytes()));
		
		return answerHash.equalsIgnoreCase(hash);
	}
	
}
