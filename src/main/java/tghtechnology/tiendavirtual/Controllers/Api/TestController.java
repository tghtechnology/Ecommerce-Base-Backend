package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {
	
	@GetMapping
    public ResponseEntity<Void> listarCategoria(@RequestHeader Map<String, String> headers){
		headers.entrySet().forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}