package tghtechnology.tiendavirtual.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;

@Service
@AllArgsConstructor
public class CategoriaService {

	@Autowired
    private CategoriaRepository catRepository;

    /*Listar categoria*/
    public List<CategoriaDTOForList> listarCategoria (){
        List<CategoriaDTOForList> categoriaList = new ArrayList<>();
        List<Categoria> cats = (List<Categoria>) catRepository.listarCategoria();
        
        cats.forEach( x -> {
            categoriaList.add(new CategoriaDTOForList(x));
        });
        return categoriaList;
    }
    
    /*Obtener una categoría especifica*/
    public CategoriaDTOForList listarUno( Integer id){
        Categoria categoria = buscarPorId(id);
        return new CategoriaDTOForList(categoria);
    }
    
    /**Registrar nueva categoría*/
    public CategoriaDTOForList crearCategoria(CategoriaDTOForInsert iCat){
        Categoria cat = new Categoria();
        cat.setDescripcion(iCat.getDescripcion());
        cat.setText_id(iCat.getDescripcion().strip().replace(' ', '_'));
        cat.setFecha_creacion(LocalDateTime.now());
        cat.setEstado(true);

        catRepository.save(cat);
        return new CategoriaDTOForList(cat);
    }
    
    /*Actualizar categoría */
    public void actualizarCategoria(Integer id, CategoriaDTOForInsert body){
        Categoria categoria = buscarPorId(id);
        categoria.setDescripcion(body.getDescripcion());
        categoria.setFecha_creacion(LocalDateTime.now());
        catRepository.save(categoria);
    }
    
    /**Eliminar categoría */
    public void eliminarCategoria(Integer id){
        Categoria cat = buscarPorId(id);
        cat.setEstado(false);
        cat.setText_id(cat.getId_categoria() + "%DELETED%" + cat.getText_id());
        catRepository.save(cat);
    }
    
    
    private Categoria buscarPorId(Integer id) {
		return catRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("categoria"));
	}
}