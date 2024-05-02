package tghtechnology.tiendavirtual.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tghtechnology.tiendavirtual.Enums.DistritoLima;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.DistritoDelivery;
import tghtechnology.tiendavirtual.Repository.DistritoRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.DistritoDelivery.DistritoDeliveryDTOForInsert;
import tghtechnology.tiendavirtual.dto.DistritoDelivery.DistritoDeliveryDTOForList;

@Slf4j
@Service
@AllArgsConstructor
public class DistritoDeliveryService implements ApplicationListener<ApplicationReadyEvent>{

    private DistritoRepository ddRepository;

    /**
     * Lista todos los distritos activos.<br>
     * Si el usuario que lo solicita es de tipo Gerente, muestra también los inactivos 
     * @param activo Si se quiere listar los activos o no activos. Si es null lista ambos
     * @param auth La autenticacion del usuario
     * @return Una lista de los distritos en formato ForList
     */
    public List<DistritoDeliveryDTOForList> listarDistritos (Boolean activo, Authentication auth){

    	List<DistritoDelivery> dists;
    	
    	if(esGerente(auth))
    		dists = ddRepository.listar();
    	else
    		dists = ddRepository.listarPorActivo(true);
    	
    	return dists
    			.stream()
    			.map(new DistritoDeliveryDTOForList()::from)
    			.sorted()
    			.toList();
    	
    }
    
    /**
     * Obtiene un distrito en específico según su ID.<br>
     * Si el distrito esta desactivado, no lo muesta a menos que el usuario sea gerente.
     * 
     * @param id la ID del distrito
     * @param auth la autenticacion del usuario
     * @return El distrito encontrado en formato ForList o null si no existe
     */
    public DistritoDeliveryDTOForList listarUno(DistritoLima id, Authentication auth){
    	DistritoDelivery dist = ddRepository.listarUno(id).orElse(null);
    	
    	if(!esGerente(auth))
    		if(dist != null && dist.getActivo() == false)
    			dist = null;
    	
    	return dist == null ? null : new DistritoDeliveryDTOForList().from(dist);
    }
    
    /**
     * Modifica un distrito.
     * 
     * @param id ID del distrito a modificar
     * @param mDist Datos del distrito en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningun distrito
     */
    public void actualizarDistrito(DistritoLima id, DistritoDeliveryDTOForInsert iDist){
        DistritoDelivery dist = buscarPorId(id);
        dist = iDist.updateModel(dist);
        ddRepository.save(dist);
    }
    
    /**
     * Modifica el estado un distrito.
     * 
     * @param id ID del distrito a modificar
     * @param activo Si el distrito pasa a ser activo o inactivo
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningun distrito
     */
    public void actualizarActivo(DistritoLima id, boolean activo) {
    	DistritoDelivery dist = buscarPorId(id);
        dist.setActivo(activo);
        ddRepository.save(dist);
    }
    
    public boolean esGerente(Authentication auth) {
    	return auth != null && TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE);
    }
    
    public DistritoDelivery buscarPorId(DistritoLima id) throws IdNotFoundException{
		return ddRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("distrito"));
	}

    /**
     * Al inicializarse la aplicación, si la tabla de distritos está vacía,
     * la pobla con todos los distritos del enumerador
     */
    @Transactional(rollbackOn = {DataIntegrityViolationException.class})
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

    	if(ddRepository.count() == 0) {
    		log.info("Tabla de distritos vacía, llenando distritos...");
    		
    		List<DistritoDelivery> dds = new ArrayList<>();
    		for(DistritoLima dist : DistritoLima.values()) {
    			dds.add(new DistritoDelivery(dist, BigDecimal.ZERO, false));
    		}
    		log.info("Finalizado el llenado de distritos");
    	}
		
	}
    
    
    
}