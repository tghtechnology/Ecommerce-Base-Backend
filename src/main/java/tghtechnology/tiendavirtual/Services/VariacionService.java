package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
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
     * @return la vriación creada en formato ForList
     */
    public VariacionDTOForList crearVariacionItem(VariacionDTOForInsert iVar){
    	
        Variacion var = iVar.toModel();
        
        Item item = item_buscarPorId(iVar.getId_item());
        var.setItem(item);
        
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
        varRepository.save(var);
    }
    
    public Item item_buscarPorId(Integer id) throws IdNotFoundException{
		return itemRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
    public Variacion buscarPorId(Integer id) throws IdNotFoundException{
		return varRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("variacion"));
	}
}