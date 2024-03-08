package tghtechnology.chozaazul.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tghtechnology.chozaazul.Models.Enums.Mes;
import tghtechnology.chozaazul.Services.MenuService;
import tghtechnology.chozaazul.dto.MenuCumple.MenuCumpleDTOForList;

@RequestMapping("/api/menu-cumple")
@RestController
public class MenuController {

	@Autowired
    private MenuService menuService;
	
	@GetMapping
	public ResponseEntity<List<MenuCumpleDTOForList>> listar(@RequestParam(required = false, name = "mes") Mes mes){
		List<MenuCumpleDTOForList> menus;
		
		if(mes == null)
			menus = menuService.listar();
		else 
			menus = menuService.listarPorMes(mes);
		
		return ResponseEntity.status(HttpStatus.OK).body(menus);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MenuCumpleDTOForList> listarUno(@PathVariable Integer id) {
		MenuCumpleDTOForList pla = menuService.listarUno(id); 
            return ResponseEntity.status(HttpStatus.OK).body(pla);
	}

}
