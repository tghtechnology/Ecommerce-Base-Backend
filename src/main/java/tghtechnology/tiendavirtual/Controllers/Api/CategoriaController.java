package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tghtechnology.tiendavirtual.Services.CategoriaService;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;

@RequestMapping("/api/categoria")
@RestController
public class CategoriaController {

	@Autowired
    private CategoriaService catService;
	
	@GetMapping
    public ResponseEntity<List<CategoriaDTOForList>> listarCategoria(){
        List<CategoriaDTOForList> cats = catService.listarCategoria();
        return ResponseEntity.status(HttpStatus.OK).body(cats);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTOForList> ListarUno(@PathVariable Integer id) {
        CategoriaDTOForList cat = catService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(cat);
	}

}