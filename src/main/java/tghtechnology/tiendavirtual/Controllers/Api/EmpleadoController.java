package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.EmpleadoService;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForList;

@RequestMapping("/api/empleado")
@RestController
public class EmpleadoController {

	@Autowired
    private EmpleadoService empService;
	
	@Gerente
	@GetMapping
    public ResponseEntity<List<EmpleadoDTOForList>> listarEmpleados(@RequestParam(defaultValue = "1", name = "page") Integer page){
        List<EmpleadoDTOForList> emps = empService.listarEmpleados(page);
        return ResponseEntity.status(HttpStatus.OK).body(emps);
    }
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<EmpleadoDTOForList> ListarUno(@PathVariable Integer id) {
		EmpleadoDTOForList emp = empService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(emp);
	}
	
	@Gerente
	@PostMapping
	public ResponseEntity<EmpleadoDTOForList> crearEmpleado(@RequestBody @Valid EmpleadoDTOForInsert iEmp,
															Authentication auth){
		EmpleadoDTOForList emp = empService.crearEmpleado(iEmp, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(emp);
	}
	
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> actualizarEmpleado(@PathVariable Integer id,
													@RequestBody @Valid EmpleadoDTOForInsert iEmp,
													Authentication auth){
		empService.actualizarEmpleado(id, iEmp, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id,
													Authentication auth){
		empService.eliminarEmpleado(id, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}