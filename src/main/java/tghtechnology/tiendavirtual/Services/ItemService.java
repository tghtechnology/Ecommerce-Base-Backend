package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoImagen;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Repository.DescuentoRepository;
import tghtechnology.tiendavirtual.Repository.ImagenRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
import tghtechnology.tiendavirtual.Utils.Cloudinary.MediaManager;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForInsert;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForListFull;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
	private DescuentoRepository desRepository;
	private CategoriaRepository catRepository;
	private VariacionRepository varRepository;
	private ImagenRepository imaRepository;
	
	private MarcaService marService;
	private MediaManager mediaManager;
	private SettingsService settings;

    /*Listar item*/
    public List<ItemDTOForList> listar (String query,
									    BigDecimal min,
									    BigDecimal max,
									    String categoria,
									    Integer pagina,
									    Authentication auth
    		){
        List<ItemDTOForList> itemList = new ArrayList<>();
        
        if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
        
        Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.items"));
        List<Item> items = (List<Item>) itemRepository.listar(query, min, max, categoria, pag);
        
        Boolean extendedPermission = (auth != null 
        								&& auth.getAuthorities() != null
        								&& TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE));
        
        items.forEach( x -> {
            itemList.add(new ItemDTOForList().from(x, imaRepository.listarPorObjeto(TipoImagen.PRODUCTO, x.getId_item()), extendedPermission));
        });
        return itemList;
    }
    
    /*Obtener un item especifico*/
    public ItemDTOForListFull listarUno(Integer id){
    	Item item = itemRepository.listarUno(id).orElse(null);
        return item == null ? null : new ItemDTOForListFull().from(item, imaRepository.listarPorObjeto(TipoImagen.PRODUCTO, item.getId_item()));
    }
    
    /*Obtener un item especifico*/
    public ItemDTOForListFull listarUno(String text_id){
    	Item item = buscarPorId(text_id);
        return new ItemDTOForListFull().from(item, imaRepository.listarPorObjeto(TipoImagen.PRODUCTO, item.getId_item()));
    }
    
    /**Registrar nuevo item
     * @throws IOException */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public ItemDTOForList crearItem(ItemDTOForInsert iItem, MultipartFile imagen) throws IOException, DataIntegrityViolationException{
 
    	Item item = iItem.toModel();
    	if(itemRepository.listarUno(item.getText_id()).isPresent())
    		throw new DataIntegrityViolationException("El nombre (" + item.getText_id() + ") ya existe para producto.");
    	
    	Categoria cat = cat_buscarPorId(iItem.getId_categoria());
    	item.setCategoria(cat);
    	
    	Marca mar = marService.buscarPorId(iItem.getId_marca());
    	item.setMarca(mar);
    	
    	Item item2 = itemRepository.save(item); // Asignar a otra instancia para que no muera la transaccion
    	
    	Variacion var = iItem.toVariacion();
    	var.setItem(item2);
    	var.setCorrelativo(1);
    	
    	var = varRepository.save(var);
    	
    	item2.getVariaciones().add(var);
    	
    	if(imagen != null) {
	        Imagen img = mediaManager.subirImagenItem(item2.getText_id(), imagen);
	        img.setId_owner(item.getId_item());
	        img.set_index(1);
			img = imaRepository.save(img);
			
			return new ItemDTOForList().from(item2, List.of(img), true);
        }
    	return new ItemDTOForList().from(item2, true);
    }
    
    /*Actualizar item */
    @Transactional(rollbackFor = {IdNotFoundException.class})
    public void actualizarItem(Integer id, ItemDTOForInsert mItem){
    	
    	Item i = buscarPorId(id);
    	
    	Optional<Item> tmp = itemRepository.listarUno(Item.transform_id(mItem.getNombre()));
    	
    	if(tmp.isEmpty() || tmp.get().getId_item().equals(i.getId_item())) {
    		
    		Item item = mItem.updateModel(i);
    		
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
    
    /**Eliminar item */
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
    
    /**Añadir imagen 
     * @throws IOException */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public void addImagen(Integer id, MultipartFile imagen) throws IOException {
    	Item item = buscarPorId(id);
    	
    	List<Imagen> imagenes = imaRepository.listarPorObjeto(TipoImagen.PRODUCTO, id);
    	
    	Imagen img = mediaManager.subirImagenItem(item.getText_id(), imagen);
        img.setId_owner(item.getId_item());
        img.set_index(imagenes.size()+1);
		img = imaRepository.save(img);
    }
    
    /** Elimina una imagen
     * @throws Exception */
    public void eliminarImagen(Integer id, Integer index) throws Exception {
    	Item item = buscarPorId(id);
    	
    	List<Imagen> imagenes = imaRepository.listarPorObjeto(TipoImagen.PRODUCTO, item.getId_item());
    	
    	Imagen img = imagenes
						.stream()
						.filter(i -> i.get_index() == index)
						.findFirst()
						.orElseThrow(() -> new IdNotFoundException("imagen"));
    	
    	mediaManager.eliminarImagenes(List.of(img.getPublic_id_Imagen(), img.getPublic_id_Miniatura()));
    	imaRepository.delete(img);
    	
    	imagenes = imagenes
    				.stream()
    				.filter(i -> i.get_index() > index)
    				.collect(Collectors.toList());
    	
    	imagenes.forEach(i -> i.set_index(i.get_index()-1));
    	imaRepository.saveAll(imagenes);
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
    
    public Categoria cat_buscarPorId(Integer id){
		return catRepository.listarUno(id).orElse(null);
	}

}
