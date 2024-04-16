package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Repository.ImagenRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
import tghtechnology.tiendavirtual.Utils.Cloudinary.MediaManager;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForInsert;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForList;

@Service
@AllArgsConstructor
public class VariacionService {

	@Autowired
    private VariacionRepository varRepository;
	private ItemRepository itemRepository;
	private ImagenRepository imaRepository;
	
	private MediaManager mediaManager;

    /**
     * Lista todas las variaciones no eliminadas de un producto
     * @return Una lista de las variaciones en formato ForList
     */
    public List<VariacionDTOForList> listarVariacionItem (Integer id_item){
    	
    	Item item = item_buscarPorId(id_item);
    	
    	
        List<VariacionDTOForList> varList = new ArrayList<>();
        List<Variacion> vars = (List<Variacion>) varRepository.listarPorItem(item);
        
        vars.forEach( x -> {
        	varList.add(new VariacionDTOForList().from(x));
        });
        return varList;
    }
    
    /**
     * Obtiene una variación en específico según su ID
     * @param id la ID de la variación
     * @return la variación encontrada en formato ForList o null si no existe
     */
    public VariacionDTOForList listarUno(Integer id){
        Variacion variacion = varRepository.listarUno(id).orElse(null);
        return variacion == null ? null : new VariacionDTOForList().from(variacion);
    }
    
    /**
     * Registra una nueva variación
     * @param iVar Variación en formato ForInsert
     * @param imagen La imagen en formato MultipartFile
     * @return la vriación creada en formato ForList
     * @throws IOException Si hay un error al subir la imagen
     */
    public VariacionDTOForList crearVariacionItem(VariacionDTOForInsert iVar, MultipartFile imagen) throws IOException{
    	
        Variacion var = iVar.toModel();
        
        Item item = item_buscarPorId(iVar.getId_item());
        var.setItem(item);
        var.setCorrelativo(item.getVariaciones().size()+1);
        
        Imagen img = mediaManager.subirImagenItem(var.composite_text_id(), imagen);
        img.setId_owner(var.getId_variacion());
		img = imaRepository.save(img);
		var.setImagen(img);
        
        varRepository.save(var);
        return new VariacionDTOForList().from(var);
    }
    
    /**
     * Modifica una variación
     * @param id ID de la variación a modificar
     * @param mVar Datos de la variación en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna variación
     */
    public void actualizarVariacionItem(Integer id, VariacionDTOForInsert mVar){
        Variacion variacion = buscarPorId(id);
        variacion = mVar.updateModel(variacion);
        varRepository.save(variacion);
    }
    
    public void actualizarImagen(Integer id, MultipartFile imagen) throws Exception {
    	Variacion var = buscarPorId(id);
    	Imagen old_img = var.getImagen();
    	
    	mediaManager.eliminarImagenes(List.of(old_img.getPublic_id_Imagen(), old_img.getPublic_id_Miniatura()));
    	
    	
    	Imagen new_img = mediaManager.subirImagenVariacion(var.composite_text_id(), imagen);
    	new_img.setId_owner(var.getId_variacion());
    	var.setImagen(new_img);
    	
    	imaRepository.save(new_img);
    	varRepository.save(var);
    	
    	imaRepository.delete(old_img);
    	
    }
    
    public Integer restock(Integer id, Integer cantidad) {
    	Variacion var = buscarPorId(id);
    	
    	var.setStock(var.getStock() + cantidad);
    	if(var.getDisponibilidad() == DisponibilidadItem.SIN_STOCK);
    		var.setDisponibilidad(DisponibilidadItem.NO_DISPONIBLE);
    	
    	varRepository.save(var);
    	return var.getStock();
    	
    }
    
    /**
     * Realiza un eliminado lógico de una variación
     * @param id ID de la variación a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna variación
     */
    public void eliminarVariacionItem(Integer id){
        Variacion var = buscarPorId(id);
        if(var.getItem().getVariaciones().stream().filter(v -> v.getEstado()).collect(Collectors.toList()).size() == 1)
        	throw new DataMismatchException("variacion", "No se puede eliminar la única variación de un item");
        var.setEstado(false);
        
        List<Variacion> vars = var.getItem()
        		.getVariaciones()
        		.stream()
        		.filter(v -> (v.getEstado() && v.getCorrelativo() > var.getCorrelativo()))
        		.collect(Collectors.toList());
        
        vars.forEach( v -> v.setCorrelativo(v.getCorrelativo()-1));
        
        varRepository.save(var);
        varRepository.saveAll(vars);
    }
    
    public Item item_buscarPorId(Integer id) throws IdNotFoundException{
		return itemRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
    public Variacion buscarPorId(Integer id) throws IdNotFoundException{
		return varRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("variacion"));
	}
}