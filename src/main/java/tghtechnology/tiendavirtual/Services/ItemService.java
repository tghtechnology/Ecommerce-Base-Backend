package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Models.VariacionItem;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Repository.DescuentoRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForInsert;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
	private DescuentoRepository desRepository;
	private CategoriaRepository catRepository;
	private VariacionRepository varRepository;
	
	private MarcaService marService;

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
    @Transactional(rollbackFor = {IdNotFoundException.class})
    public ItemDTOForList crearItem(ItemDTOForInsert iItem){
 
    	Item item = iItem.toModel();
    	
    	if(itemRepository.listarUno(item.getText_id()).isPresent())
    		throw new DataIntegrityViolationException("El nombre (" + item.getText_id() + ") ya existe para producto.");
    	
    	Categoria cat = cat_buscarPorId(iItem.getId_categoria());
    	item.setCategoria(cat);
    	
    	Marca mar = marService.buscarPorId(iItem.getId_marca());
    	item.setMarca(mar);
    	
    	item = itemRepository.save(item);
    	
    	VariacionItem var = iItem.toVariacion();
    	var.setItem(item);
    	var = varRepository.save(var);
    	
    	item.getVariaciones().add(var);
    	
    	return new ItemDTOForList().from(item);
    }
    
    /*Actualizar plato */
    @Transactional(rollbackFor = {IdNotFoundException.class})
    public void actualizarItem(Integer id, ItemDTOForInsert mItem){
    	
    	Item item = buscarPorId(id);
    	
    	Optional<Item> tmp = itemRepository.listarUno(Item.transform_id(mItem.getNombre()));
    	
    	if(tmp.isEmpty() || tmp.get().getId_item().equals(item.getId_item())) {
    		
    		item = mItem.updateModel(item);
    		
    		if(item.getCategoria().getId_categoria() != mItem.getId_categoria()) {
    			Categoria cat = cat_buscarPorId(mItem.getId_categoria());
        		item.setCategoria(cat);
    		}
    		if(item.getMarca().getId_marca() != mItem.getId_marca()) {
    			Marca mar = marService.buscarPorId(mItem.getId_marca());
        		item.setMarca(mar);
    		}
    		if(item.getDescuento().getId_descuento() != mItem.getId_descuento()) {
    			if(mItem.getId_descuento() == null) {
    				item.setDescuento(null);
    			} else {
    				Descuento des = des_buscarPorId(mItem.getId_descuento());
    				des.setActivado(true);
    				des = desRepository.save(des);
    				
    				if(item.getDescuento() != null) {
    					item.getDescuento().setActivado(false);
    					desRepository.save(item.getDescuento());
    				}
    				
    				item.setDescuento(des);
    			}
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
        
        // Elimina los descuentos
        item.getDescuentos().forEach(desc -> {
        	desc.setActivado(false);
        	desc.setEstado(false);
        });
        desRepository.saveAll(item.getDescuentos());
        itemRepository.save(item);
    }
    
    public Item buscarPorId(Integer id) {
		return itemRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    private Item buscarPorId(String text_id) {
		return itemRepository.listarUno(text_id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
    public Descuento des_buscarPorId(Integer id) throws IdNotFoundException{
		return desRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("descuento"));
	}
    
    public Categoria cat_buscarPorId(Integer id) throws IdNotFoundException{
		return catRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("categoria"));
	}

}
