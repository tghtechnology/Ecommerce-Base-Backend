package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForInsert;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;

@Service
@AllArgsConstructor
public class ItemService {

	@Autowired
    private ItemRepository itemRepository;
	private CategoriaRepository catRepo;

    /*Listar plato*/
    public List<ItemDTOForList> listar (String query,
									    BigDecimal min,
									    BigDecimal max,
									    String categoria
    		){
        List<ItemDTOForList> platoList = new ArrayList<>();
        List<Item> plas = (List<Item>) itemRepository.listar(query, min, max, categoria);
        
        plas.forEach( x -> {
            platoList.add(new ItemDTOForList().from(x));
        });
        return platoList;
    }
    
    /*Obtener un plato especifico*/
    public ItemDTOForList listarUno(Integer id){
    	Item plato = buscarPorId(id);
        return new ItemDTOForList().from(plato);
    }
    
    /*Obtener un plato especifico*/
    public ItemDTOForList listarUno(String text_id){
    	Item plato = buscarPorId(text_id);
        return new ItemDTOForList().from(plato);
    }
    
    /**Registrar nuevo plato
     * @throws IOException */
    public ItemDTOForList crearItem(ItemDTOForInsert iItem){
 
    	Item item = iItem.toModel();
    	
    	if(itemRepository.listarUno(item.getText_id()).isPresent())
    		throw new DataIntegrityViolationException("El nombre (" + item.getText_id() + ") ya existe para producto.");
    	
    	Categoria cat = obtenerCategoria(iItem.getId_categoria());
    	item.setCategoria(cat);
    	
    	item = itemRepository.save(item);
    	
    	return new ItemDTOForList().from(item);
    }
    
    /*Actualizar plato */
    public void actualizarItem(Integer id, ItemDTOForInsert body){
    	
    	Item item = buscarPorId(id);
    	
    	Optional<Item> tmp = itemRepository.listarUno(body.transform_id());
    	
    	if(tmp.isEmpty() || tmp.get().getId_item().equals(item.getId_item())) {
    		
    		item = body.updateModel(item);
    		itemRepository.save(item);
    		
    	} else {
    		throw new DataMismatchException("nombre", "Ya existe un item con ese nombre");
    	}
    }
    
    /**Eliminar plato */
    public void eliminarItem(Integer id){
    	Item item = buscarPorId(id);
        item.setEstado(false);
        item.setText_id(item.getId_item() + "%DELETED%" + item.getText_id());
        itemRepository.save(item);
    }
    
    private Item buscarPorId(Integer id) {
		return itemRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    private Item buscarPorId(String text_id) {
		return itemRepository.listarUno(text_id).orElseThrow( () -> new IdNotFoundException("item"));
	}  
    
	private Categoria obtenerCategoria(int id) {
		return catRepo.listarUno(id).orElseThrow(() -> new IdNotFoundException("categoria"));
	}

}
