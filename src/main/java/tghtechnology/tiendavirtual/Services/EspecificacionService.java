package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Especificacion;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Repository.EspecificacionRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.EspecificacionItem.EspecificacionDTOForInsert;
import tghtechnology.tiendavirtual.dto.EspecificacionItem.EspecificacionDTOForList;

@Service
@AllArgsConstructor
public class EspecificacionService {

	@Autowired
	private EspecificacionRepository espRepository;
    private VariacionRepository varRepository;

    /**
     * Lista todas las especificaciones no eliminadas de una especificación
     * @return Una lista de las especificaciones en formato ForList
     */
    public List<EspecificacionDTOForList> listarEspecificacionVariacion (Integer id_var){
    	
    	Variacion var = var_buscarPorId(id_var);
    	
    	
        List<EspecificacionDTOForList> espList = new ArrayList<>();
        List<Especificacion> esps = espRepository.listarPorVariacion(var);
        
        esps.forEach( x -> {
        	espList.add(new EspecificacionDTOForList().from(x));
        });
        return espList;
    }
    
    /**
     * Obtiene una especificación en específico según su ID
     * @param id la ID de la especificación
     * @return la especificación encontrada en formato ForList o null si no existe
     */
    public EspecificacionDTOForList listarUno(Integer id){
        Especificacion esp = espRepository.listarUno(id).orElse(null);
        return esp == null ? null : new EspecificacionDTOForList().from(esp);
    }
    
    /**
     * Registra una nueva especificación
     * @param iEsp Especificación en formato ForInsert
     * @return la vriación creada en formato ForList
     * @throws IOException Si hay un error al subir la imagen
     */
    public EspecificacionDTOForList crearEspecificacion(EspecificacionDTOForInsert iEsp) throws IOException{
    	
        Especificacion esp = iEsp.toModel();
        
        Variacion var = var_buscarPorId(iEsp.getId_variacion());
        esp.setVariacion(var);
        esp.setCorrelativo(var.getEspecificaciones().size()+1);
        
        espRepository.save(esp);
        return new EspecificacionDTOForList().from(esp);
    }
    
    /**
     * Modifica una especificación
     * @param id ID de la especificación a modificar
     * @param mEsp Datos de la especificación en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna especificación
     */
    public void actualizarEspecificacion(Integer id, EspecificacionDTOForInsert mEsp){
    	Especificacion esp = buscarPorId(id);
        esp = mEsp.updateModel(esp);
        espRepository.save(esp);
    }
    
    /**
     * Realiza un eliminado lógico de una especificación
     * @param id ID de la especificación a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna especificación
     */
    public void eliminarEspecificacion(Integer id){
    	Especificacion esp = buscarPorId(id);
        if(esp.getVariacion().getEspecificaciones().stream().filter(v -> v.getEstado()).collect(Collectors.toList()).size() == 1)
        	throw new DataMismatchException("especificacion", "No se puede eliminar la única especificación de un item");
        esp.setEstado(false);
        
        List<Especificacion> esps = esp.getVariacion()
        		.getEspecificaciones()
        		.stream()
        		.filter(v -> (v.getEstado() && v.getCorrelativo() > esp.getCorrelativo()))
        		.collect(Collectors.toList());
        
        esps.forEach( v -> v.setCorrelativo(v.getCorrelativo()-1));
        
        espRepository.save(esp);
        espRepository.saveAll(esps);
    }
    
    private Variacion var_buscarPorId(Integer id) throws IdNotFoundException{
		return varRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("variacion"));
	}
    
    private Especificacion buscarPorId(Integer id) throws IdNotFoundException{
		return espRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("especificacion"));
	}
    
}