package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.SettingsService;
import tghtechnology.tiendavirtual.dto.Setting.SettingDTOForInsert;
import tghtechnology.tiendavirtual.dto.Setting.SettingDTOForList;

@RequestMapping("/api/setting")
@RestController
public class SettingController {

	@Autowired
    private SettingsService setService;
	
	@Gerente
	@GetMapping
    public ResponseEntity<List<SettingDTOForList>> listar(){
        List<SettingDTOForList> sets = setService.listarSettings();
        return ResponseEntity.status(HttpStatus.OK).body(sets);
    }
	
	@Gerente
	@PutMapping
	public ResponseEntity<Void> modificar(@RequestBody @Valid List<SettingDTOForInsert> settings){
		setService.modificarSettings(settings);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}