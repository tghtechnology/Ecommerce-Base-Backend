package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.MarcaRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForInsert;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
	private CategoriaRepository catRepo;
	private MarcaRepository marRepo;

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
    	Item item = itemRepository.listarUno(id).orElse(null);
        return item == null ? null : new ItemDTOForList().from(item);
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
    	
    	Marca mar = obtenerMarca(iItem.getId_marca());
    	item.setMarca(mar);
    	
    	item = itemRepository.save(item);
    	
    	return new ItemDTOForList().from(item);
    }
    
    /*Actualizar plato */
    public void actualizarItem(Integer id, ItemDTOForInsert mItem){
    	
    	Item item = buscarPorId(id);
    	
    	Optional<Item> tmp = itemRepository.listarUno(Item.transform_id(mItem.getNombre()));
    	
    	if(tmp.isEmpty() || tmp.get().getId_item().equals(item.getId_item())) {
    		
    		item = mItem.updateModel(item);
    		
    		if(item.getCategoria().getId_categoria() != mItem.getId_categoria()) {
    			Categoria cat = obtenerCategoria(mItem.getId_categoria());
        		item.setCategoria(cat);
    		}
    		if(item.getMarca().getId_marca() != mItem.getId_marca()) {
    			Marca mar = obtenerMarca(mItem.getId_marca());
        		item.setMarca(mar);
    		}
    		
    		itemRepository.save(item);
    		
    	} else {
    		throw new DataIntegrityViolationException("El nombre (" + tmp.get().getText_id() + ") ya existe para producto.");
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
	
	private Marca obtenerMarca(int id) {
		return marRepo.listarUno(id).orElseThrow(() -> new IdNotFoundException("marca"));
	}

}
