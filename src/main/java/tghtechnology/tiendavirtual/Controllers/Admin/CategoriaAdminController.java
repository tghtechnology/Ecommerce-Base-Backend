package tghtechnology.tiendavirtual.Controllers.Admin;

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
import tghtechnology.tiendavirtual.Services.CategoriaService;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;

@RequestMapping("/admin/categoria")
@RestController
public class CategoriaAdminController {

	@Autowired
    private CategoriaService catService;
	
	@Empleado
	@GetMapping
    public ResponseEntity<List<CategoriaDTOForList>> listarCategoria(){
            List<CategoriaDTOForList> cats = catService.listarCategoria();
            return ResponseEntity.status(HttpStatus.OK).body(cats);
    }
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTOForList> ListarUno(@PathVariable Integer id) {
	        CategoriaDTOForList cat = catService.listarUno(id);

	        if (cat != null) {
	            return ResponseEntity.status(HttpStatus.OK).body(cat);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	}
	
	@Empleado
	@PostMapping    
    public ResponseEntity<CategoriaDTOForList> crearCategoria(@Valid @RequestBody CategoriaDTOForInsert categoria){
            CategoriaDTOForList newCategoria = catService.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
    }
	
	@Empleado
	@PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaDTOForInsert body){
        if(body.getDescripcion() == null || body.getDescripcion().strip().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        catService.actualizarCategoria(id, body);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
	
	@Gerente
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id){
            catService.eliminarCategoria(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

}