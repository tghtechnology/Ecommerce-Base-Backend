package tghtechnology.chozaazul.Controllers.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.chozaazul.Services.EmpleadoService;
import tghtechnology.chozaazul.Utils.Security.Interfaces.Empleado;
import tghtechnology.chozaazul.Utils.Security.Interfaces.Gerente;
import tghtechnology.chozaazul.dto.Empleado.EmpleadoDTOForInsert;
import tghtechnology.chozaazul.dto.Empleado.EmpleadoDTOForList;
import tghtechnology.chozaazul.dto.Empleado.EmpleadoDTOForModify;

@RestController
@RequestMapping("/admin/empleado")
public class EmpleadoAdminController {

    @Autowired
    private EmpleadoService empService;
    
    @Empleado
    @GetMapping
    public ResponseEntity<List<EmpleadoDTOForList>> listarEmpleado(){
            List<EmpleadoDTOForList> lista = empService.listarEmpleados();
            return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @Empleado
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTOForList> listarUno(@PathVariable Integer id){
            EmpleadoDTOForList empleado = empService.listarUno(id);
            if(empleado != null){
                return ResponseEntity.status(HttpStatus.OK).body(empleado);
            }else{
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
            }
    }

    @Gerente
    @PostMapping
    public ResponseEntity<EmpleadoDTOForList> crearEmpleado(@Valid @RequestBody EmpleadoDTOForInsert emp){
            EmpleadoDTOForList newEmp = empService.crearEmpleado(emp);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmp);
    }
    
    @Gerente
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarEmpleado(@PathVariable Integer id, @Valid @RequestBody EmpleadoDTOForModify body){

        empService.actualizarEmpleado(id, body);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
    
    @Gerente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id){
            empService.eliminarEmpleado(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
