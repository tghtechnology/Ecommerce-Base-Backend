package tghtechnology.chozaazul.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Models.Plato;
import tghtechnology.chozaazul.Models.Promocion;
import tghtechnology.chozaazul.Models.Composite.PromocionPlato;
import tghtechnology.chozaazul.Repository.PlatoRepository;
import tghtechnology.chozaazul.Repository.PromocionPlatoRepository;
import tghtechnology.chozaazul.Repository.PromocionRepository;
import tghtechnology.chozaazul.Utils.Exceptions.IdNotFoundException;
import tghtechnology.chozaazul.dto.Promocion.PromocionDTOForInsert;
import tghtechnology.chozaazul.dto.Promocion.PromocionDTOForList;
import tghtechnology.chozaazul.dto.Promocion.PromocionPlato.PromocionPlatoDTOForInsert;

@Service
@AllArgsConstructor
public class PromocionService {

	@Autowired
	private PromocionPlatoRepository prplRepository;
    private PromocionRepository promRepository;
	private PlatoRepository plaRepository;

    /**
     * Listar todas las promociones activas
     * @return Lista de promociones
     */
    public List<PromocionDTOForList> listarPromociones (){
        List<PromocionDTOForList> promociones = new ArrayList<>();
        List<Promocion> proms = promRepository.listarPromociones();
        
        proms.forEach( x -> {
            promociones.add(new PromocionDTOForList(x));
        });
        return promociones;
    }
    
    /**
     * Listar una sola promocion activa
     * @param id La id de la promocion
     * @return La promocion si se encontró
     */
    public PromocionDTOForList listarUno(Integer id){
        Promocion prom = buscarPorId(id);
        return new PromocionDTOForList(prom);
    }
    
    /**
     * Crea una nueva promocion
     * @param iProm Los datos de la promoción a crear en formato ForInsert
     * @return la promoción creada en formato ForList
     */
    public PromocionDTOForList crearPromocion(PromocionDTOForInsert iProm){
        Promocion prom = new Promocion();
        
        prom.setRepetible(iProm.isRepetible());
        prom.setDescuento(iProm.getDescuento());
        prom.setFecha_inicio(iProm.getFecha_inicio());
        prom.setFecha_finalizacion(iProm.getFecha_finalizacion());
        prom.setEstado(true);
        
        prom = promRepository.save(prom);
        
        final Set<PromocionPlato> platos = new HashSet<>();
        for(PromocionPlatoDTOForInsert prpl : iProm.getPlatos()) {
        	
        	// Plato 1
        	PromocionPlato promPlato = new PromocionPlato();
        	promPlato.setCantidad(prpl.getCantidad());
        	promPlato.setEstado(true);
        	promPlato.setPromocion(prom);
        	//Validar plato
        	Plato plato = plaRepository.listarUno(prpl.getId_plato()).orElseThrow(() -> new IdNotFoundException("plato"));
        	promPlato.setPlato(plato);
        	
        	// Plato 2
        	if(prpl.getId_plato_2() != null) {
        		Plato plato2 = plaRepository.listarUno(prpl.getId_plato_2()).orElseThrow(() -> new IdNotFoundException("plato"));
            	promPlato.setPlato2(plato2);
        	}
        	
        	// Plato 3
        	if(prpl.getId_plato_3() != null) {
        		Plato plato3 = plaRepository.listarUno(prpl.getId_plato_3()).orElseThrow(() -> new IdNotFoundException("plato"));
            	promPlato.setPlato3(plato3);
        	}
        	
        	platos.add(promPlato);
        }
        prom.setPlatos(platos);

        prplRepository.saveAll(prom.getPlatos());
        prom = promRepository.save(prom);
        return new PromocionDTOForList(prom);
    }
    
    /**
     * Modifica una promoción
     * @param id La ID de la promoción a modificar
     * @param body Los datos de la promoción a modificar en formato ForInsert
     */
    @Transactional
    public void actualizarPromocion(Integer id, PromocionDTOForInsert body){
        Promocion prom = buscarPorId(id);
        
        prom.setRepetible(body.isRepetible());
        prom.setDescuento(body.getDescuento());
        prom.setFecha_inicio(body.getFecha_inicio());
        prom.setFecha_finalizacion(body.getFecha_finalizacion());
        
        prplRepository.eliminarPorPromocion(prom);
        
        final Set<PromocionPlato> platos = new HashSet<>();
        for(PromocionPlatoDTOForInsert prpl : body.getPlatos()) {
        	
        	PromocionPlato promPlato = new PromocionPlato();
        	promPlato.setCantidad(prpl.getCantidad());
        	promPlato.setEstado(true);
        	promPlato.setPromocion(prom);
        	//Validar plato
        	Plato plato = plaRepository.listarUno(prpl.getId_plato()).orElseThrow(() -> new IdNotFoundException("plato"));;
        	promPlato.setPlato(plato);
        	
        	platos.add(promPlato);
        }
        prom.setPlatos(platos);
        
        prom.setEstado(true);

        prplRepository.saveAll(prom.getPlatos());
        prom = promRepository.save(prom);
    }
    
    /**
     * Elimina una promoción
     * @param id la ID de la promoción a eliminar
     */
    public void eliminarPromocion(Integer id){
        Promocion prom = buscarPorId(id);
        prom.setEstado(false);
        prom.getPlatos().forEach(prpl ->{
        	prpl.setEstado(true);
        });
        prplRepository.saveAll(prom.getPlatos());
        promRepository.save(prom);
    }
    
    
    private Promocion buscarPorId(Integer id) {
		return promRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("promocion"));
	}
}