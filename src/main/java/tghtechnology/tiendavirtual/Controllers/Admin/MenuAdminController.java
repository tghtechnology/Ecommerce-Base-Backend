package tghtechnology.tiendavirtual.Controllers.Admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Services.MenuService;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.dto.MenuCumple.MenuCumpleDTOForInsert;
import tghtechnology.tiendavirtual.dto.MenuCumple.MenuCumpleDTOForList;

@RequestMapping("/admin/menu-cumple")
@RestController
public class MenuAdminController {

	@Autowired
    private MenuService menuService;
	@Autowired
	private CustomBeanValidator validator;
	
	@Empleado
	@GetMapping
	public ResponseEntity<List<MenuCumpleDTOForList>> listar(@RequestParam(required = false, name = "mes") Mes mes){
		List<MenuCumpleDTOForList> menus;
		
		if(mes == null)
			menus = menuService.listar();
		else 
			menus = menuService.listarPorMes(mes);
		
		return ResponseEntity.status(HttpStatus.OK).body(menus);
	}
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<MenuCumpleDTOForList> listarUno(@PathVariable Integer id) {
		MenuCumpleDTOForList pla = menuService.listarUno(id); 
            return ResponseEntity.status(HttpStatus.OK).body(pla);
	}
	
	@Empleado
	@PostMapping    
    public ResponseEntity<MenuCumpleDTOForList> crearMenu(@Valid @RequestParam(value = "menu") String menu,
													  @RequestParam(value = "imagen", required = true) MultipartFile imagen) throws IOException, CustomValidationFailedException{
		
		MenuCumpleDTOForInsert iMen =  new ObjectMapper().readValue(menu, MenuCumpleDTOForInsert.class);
		validator.validar(iMen);
		
		MenuCumpleDTOForList newMenu = menuService.crearMenuCumple(iMen, imagen);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMenu);
    }
	
	@Empleado
	@PutMapping("/{id}")
    public ResponseEntity<Void> actualizarMenu(@PathVariable Integer id, @Valid @RequestParam(value = "menu") String menu,
			  									@RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException, CustomValidationFailedException{

		MenuCumpleDTOForInsert mMen =  new ObjectMapper().readValue(menu, MenuCumpleDTOForInsert.class);
		validator.validar(mMen);

        menuService.actualizarMenu(id, mMen, imagen);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
	
	@Empleado
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMenu(@PathVariable Integer id){
            menuService.eliminarMenu(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
