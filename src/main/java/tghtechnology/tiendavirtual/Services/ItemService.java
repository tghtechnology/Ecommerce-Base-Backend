package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Models.Especificacion;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Repository.DescuentoRepository;
import tghtechnology.tiendavirtual.Repository.EspecificacionRepository;
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
    private VariacionRepository varRepository;
    private EspecificacionRepository espRepository;
	private DescuentoRepository desRepository;
	private CategoriaRepository catRepository;
	private ImagenRepository imaRepository;
	
	private MarcaService marService;
	private MediaManager mediaManager;
	private SettingsService settings;

    /*Listar item*/
    public List<ItemDTOForList> listar (String query,
									    BigDecimal min,
									    BigDecimal max,
									    String categoria,
									    String rebaja,
									    Integer pagina,
									    Authentication auth
    		){
        List<ItemDTOForList> itemList = new ArrayList<>();
        
        if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
        Boolean extendedPermission = checkExtendedPermission(auth);
        
        Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.items"));
        List<Item> items = (List<Item>) itemRepository.listar(query, min, max, categoria, rebaja, extendedPermission, pag);
        
        
        items.forEach( x -> {
            itemList.add(new ItemDTOForList().from(x, extendedPermission));
        });
        return itemList;
    }
    
    /*Obtener un item especifico*/
    public ItemDTOForListFull listarUno(Integer id, Authentication auth){
    	Item item = itemRepository.listarUno(id).orElse(null);
        return item == null ? null : new ItemDTOForListFull().from(item, checkExtendedPermission(auth));
    }
    
    /*Obtener un item especifico*/
    public ItemDTOForListFull listarUno(String text_id, Authentication auth){
    	Item item = buscarPorId(text_id);
        return new ItemDTOForListFull().from(item, checkExtendedPermission(auth));
    }
    
    /**Registrar nuevo item
     * @throws IOException */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public ItemDTOForList crearItem(ItemDTOForInsert iItem, MultipartFile imagen) throws IOException, DataIntegrityViolationException{
 
    	Item item = iItem.toModel();
    	if(itemRepository.listarUno(item.getText_id()).isPresent())
    		throw new DataIntegrityViolationException("El nombre (" + item.getText_id() + ") ya existe para producto.");
    	
    	if(itemRepository.listarPorCodigo(item.getCodigo_item()).isPresent())
    		throw new DataIntegrityViolationException("El codigo (" + item.getCodigo_item() + ") ya existe para producto.");
    	
    	Categoria cat = cat_buscarPorId(iItem.getId_categoria());
    	item.setCategoria(cat);
    	
    	Marca mar = marService.buscarPorId(iItem.getId_marca());
    	item.setMarca(mar);
    	
    	Imagen img = mediaManager.subirImagenItem(item.getText_id(), imagen);
		img = imaRepository.save(img);
    	item.setImagen(img);
		
    	Item item2 = itemRepository.save(item); // Asignar a otra instancia para que no muera la transaccion
    	
        img.setId_owner(item2.getId_item());
		img = imaRepository.save(img);
		
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
    		if(item.getDescuento() == null || item.getDescuento().getId_descuento() != mItem.getId_descuento()) {
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
    @Transactional
    public void eliminarItem(Integer id){
    	
    	List<String> imgs = new ArrayList<>();
    	
    	Item item = buscarPorId(id);
        item.setEstado(false);
        item.setText_id(item.getId_item() + "%DELETED%" + item.getText_id());
        item.setCodigo_item(item.getId_item() + "%" + item.getCodigo_item());
        
        // Elimina los descuentos
        item.getDescuentos().forEach(desc -> {
        	desc.setActivado(false);
        	desc.setEstado(false);
        });
        desRepository.saveAll(item.getDescuentos());
        
        //Añade las imagenes para su eliminacion
        imgs.add(item.getImagen().getPublic_id_Imagen());
    	imgs.add(item.getImagen().getPublic_id_Miniatura());
        
        
        // Elimina las variaciones
        item.getVariaciones().forEach(var -> {
        	var.setEstado(false);
        	imgs.add(var.getImagen().getPublic_id_Imagen());
        	imgs.add(var.getImagen().getPublic_id_Miniatura());
        	// Elimina las especificaciones
        	var.getEspecificaciones().forEach( esp -> {
        		esp.setEstado(false);
        	});
        });
        
        itemRepository.save(item);
    }
    
    public void cambiarDisponibilidad(Integer id, DisponibilidadItem disp, Boolean cascade) {
    	Item item = buscarPorId(id);
    	
    	Set<Variacion> vars = new HashSet<>();
    	Set<Especificacion> esps = new HashSet<>();
    	
    	if(disp == DisponibilidadItem.DISPONIBLE) {
    		if(item.getVariaciones().isEmpty())
    			throw new DataMismatchException("disponibilidad", "No se puede activar un item sin variaciones");
    	}
    	item.setDisponibilidad(disp);
    	
    	if(cascade) {
    		item.getVariaciones().forEach(var -> {
    			if(var.getDisponibilidad() != DisponibilidadItem.SIN_STOCK)
    				var.setDisponibilidad(disp);
    			vars.add(var);
				var.getEspecificaciones().forEach( esp -> {
					esp.setDisponibilidad(disp);
					esps.add(esp);
				});
    		});
    	}
    	
    	itemRepository.save(item);
    	varRepository.saveAll(vars);
    	espRepository.saveAll(esps);
    	
    }
    
    private boolean checkExtendedPermission(Authentication auth) {
    	return (auth != null 
				&& auth.getAuthorities() != null
				&& TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE));
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
