package tghtechnology.tiendavirtual.Controllers.Api;

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
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.CategoriaService;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;

@RequestMapping("/api/categoria")
@RestController
public class CategoriaController {

	@Autowired
    private CategoriaService catService;
	
	@GetMapping
    public ResponseEntity<List<CategoriaDTOForList>> listar(){
        List<CategoriaDTOForList> cats = catService.listarCategoria();
        return ResponseEntity.status(HttpStatus.OK).body(cats);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTOForList> listarUno(@PathVariable Integer id) {
        CategoriaDTOForList cat = catService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(cat);
	}
	
	@Gerente
	@PostMapping
	public ResponseEntity<CategoriaDTOForList> crear(@RequestBody @Valid CategoriaDTOForInsert iCat){
		CategoriaDTOForList cat = catService.crearCategoria(iCat);
		return ResponseEntity.status(HttpStatus.CREATED).body(cat); 
	}
	
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestBody @Valid CategoriaDTOForInsert mCat){
		catService.actualizarCategoria(id, mCat);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		catService.eliminarCategoria(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}