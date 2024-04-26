package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Repository.DescuentoRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForList;

@Service
@AllArgsConstructor
public class DescuentoService {

	@Autowired
    private DescuentoRepository desRepository;
	private ItemRepository itemRepository;
	
	private ItemService itemService;

    /**
     * Lista todos los descuentos no eliminados
     * @return Una lista de los descuentos en formato ForList
     */
    public List<DescuentoDTOForList> listarDescuentos (){
        List<DescuentoDTOForList> desList = new ArrayList<>();
        List<Descuento> dess = (List<Descuento>) desRepository.listarDescuento();
        
        dess.forEach( x -> {
            desList.add(new DescuentoDTOForList().from(x));
        });
        return desList;
    }
    
    /**
     * Obtiene un descuento en específico según su ID
     * @param id la ID del descuento
     * @return El descuento encontrado en formato ForList o null si no existe
     */
    public DescuentoDTOForList listarUno(Integer id){
        Descuento descuento = desRepository.listarUno(id).orElse(null);
        return descuento == null ? null : new DescuentoDTOForList().from(descuento);
    }
    
    /**
     * Registra un nuevo descuento
     * @param iDes Descuento en formato ForInsert
     * @return El descuento creado en formato ForList
     * @throws IdNotFoundException Si la ID del item no se corresponde con ninguno.
     */
    public DescuentoDTOForList crearDescuento(DescuentoDTOForInsert iDes){
        Descuento des = iDes.toModel();
        
        Item item = itemService.buscarPorId(iDes.getId_item());
        
        des.setItem(item);
        des = desRepository.save(des);
        
        if(iDes.getActivado() != null && iDes.getActivado()) {
        	actualizarActivado(des, true);
        }
        
        return new DescuentoDTOForList().from(des);
    }
    
    /**
     * Modifica un descuento
     * @param id ID del descuento a modificar
     * @param mDes Datos del descuento en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningún descuento
     */
    public void actualizarDescuento(Integer id, DescuentoDTOForInsert mDes){
        Descuento descuento = buscarPorId(id);
        Boolean actualizarActivado = (mDes.getActivado() != null && mDes.getActivado() != descuento.getActivado());
        descuento = mDes.updateModel(descuento);
        
        if(actualizarActivado)
        	actualizarActivado(descuento, mDes.getActivado());
        else
        	desRepository.save(descuento);
    }
    
    /**
     * Realiza un eliminado lógico de un descuento
     * @param id ID del descuento a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningún descuento
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class})
    public void eliminarDescuento(Integer id){
        Descuento des = buscarPorId(id);
        des.setActivado(false);
        des.setEstado(false);
        desRepository.save(des);
        
        if(des.getItem().getDescuento().equals(des)) {
        	Item item = des.getItem();
        	item.setDescuento(null);
        	itemRepository.save(item);
        }
        
    }
    
    /**
     * Desactiva un descuento
     * @param id ID del descuento a desactivar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningún descuento
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class})
    public void actualizarActivo(Integer id, Boolean activo){
        Descuento des = buscarPorId(id);
        actualizarActivado(des, activo);
    }
    
    
    private void actualizarActivado(Descuento des, boolean activo) {
    	
    	Item item = des.getItem();
    	
    	if(activo) {
	    	Descuento oldDes = item.getDescuento();
	    	
	    	des.setActivado(true);
	    	item.setDescuento(des);
	    	itemRepository.save(item);
	    	
	    	if(oldDes != null) {
	    		oldDes.setActivado(false);
	    		desRepository.save(oldDes);
	    	}
    	} else {
    		des.setActivado(false);
            desRepository.save(des);
            
            if(item.getDescuento() != null && item.getDescuento().equals(des)) {
            	item.setDescuento(null);
            	itemRepository.save(item);
            }
    	}
    	
    }
    
    
    public Descuento buscarPorId(Integer id) throws IdNotFoundException{
		return desRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("descuento"));
	}
}