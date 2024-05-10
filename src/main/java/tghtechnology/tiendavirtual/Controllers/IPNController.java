package tghtechnology.tiendavirtual.Controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Services.UsuarioService;

@RestController
@AllArgsConstructor
@RequestMapping("/ipn")
public class IPNController {

	@Autowired
	private UsuarioService usService;

	// Registrar usuario por defecto
	@PostMapping
	public ResponseEntity<Map<String, Object>> crearAdmin(@RequestBody String body) throws Exception {

		String decoded = URLDecoder.decode(body, StandardCharsets.UTF_8.name());
		
		return new ResponseEntity<Map<String, Object>>(new HashMap<>(), HttpStatus.OK);
		
	}
}
