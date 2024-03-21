package tghtechnology.tiendavirtual.Services;

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
public class DireccionService {

	@Autowired
    private CategoriaRepository catRepository;

    /**
     * Lista todas las categorías no eliminadas
     * @return Una lista de las categorías en formato ForList
     */
    public List<CategoriaDTOForList> listarCategoria (){
        List<CategoriaDTOForList> categoriaList = new ArrayList<>();
        List<Categoria> cats = (List<Categoria>) catRepository.listarCategoria();
        
        cats.forEach( x -> {
            categoriaList.add(new CategoriaDTOForList().from(x));
        });
        return categoriaList;
    }
    
    /**
     * Obtiene una categoría en específico según su ID
     * @param id la ID de la categoría
     * @return la categoría encontrada en formato ForList o null si no existe
     */
    public CategoriaDTOForList listarUno(Integer id){
        Categoria categoria = catRepository.listarUno(id).orElse(null);
        return categoria == null ? null : new CategoriaDTOForList().from(categoria);
    }
    
    /**
     * Registra una nueva categoría
     * @param Categoría en formato ForInsert
     * @return la categoría creada en formato ForList
     */
    public CategoriaDTOForList crearCategoria(CategoriaDTOForInsert iCat){
        Categoria cat = iCat.toModel();
        catRepository.save(cat);
        return new CategoriaDTOForList().from(cat);
    }
    
    /**
     * Modifica una categoría
     * @param id ID de la categoría a modificar
     * @param mCat Datos de la categoría en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna categoría
     */
    public void actualizarCategoria(Integer id, CategoriaDTOForInsert mCat){
        Categoria categoria = buscarPorId(id);
        categoria = mCat.updateModel(categoria);
        catRepository.save(categoria);
    }
    
    /**
     * Realiza un eliminado lógico de una categoría
     * @param id ID de la categoría a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna categoría
     */
    public void eliminarCategoria(Integer id){
        Categoria cat = buscarPorId(id);
        cat.setEstado(false);
        cat.setText_id(cat.getId_categoria() + "%DELETED%" + cat.getText_id());
        catRepository.save(cat);
    }
    
    
    private Categoria buscarPorId(Integer id) throws IdNotFoundException{
		return catRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("categoria"));
	}
}