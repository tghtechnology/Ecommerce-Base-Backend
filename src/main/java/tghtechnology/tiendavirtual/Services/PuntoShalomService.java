package tghtechnology.tiendavirtual.Services;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.RegionPeru;
import tghtechnology.tiendavirtual.Models.PuntoShalom;
import tghtechnology.tiendavirtual.Repository.PuntoShalomRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.PuntoShalom.PuntoShalomDTOForList;

@Service
@AllArgsConstructor
public class PuntoShalomService {

    private PuntoShalomRepository psRepository;

    /**
     * Lista todos los puntos no eliminados
     * 
     * @return Una lista de los puntos Shalom en formato ForList
     */
    public List<PuntoShalomDTOForList> listarPuntos (RegionPeru departamento){
        List<PuntoShalom> pss = psRepository.listar(departamento);
        
        return pss.stream().map(ps -> new PuntoShalomDTOForList().from(ps)).collect(Collectors.toList());
    }
    
    /**
     * Obtiene un punto Shalom en específico según su ID
     * 
     * @param id la ID del punto
     * @return El punto Shalom encontrado en formato ForList o null si no existe
     */
    public PuntoShalomDTOForList listarUno(Integer id){
    	PuntoShalom ps = psRepository.listarUno(id).orElse(null);
        return ps == null ? null : new PuntoShalomDTOForList().from(ps);
    }
    
    /**
     * Carga los puntos Shalom desde un JSON
     * El JSON se encuentra en <a href="https://agencias.shalom.pe/">Agencias Shalom</a>
     * 
     * @param iCat Categoría en formato ForInsert
     * @return la categoría creada en formato ForList
     */
    public Map<String, String> cargarPuntos(List<Map<String, Object>> iPss){
    	
    	Map<String, String> results = new TreeMap<>();
    	Map<String, PuntoShalom> puntos = cargarDeDB();
    	
    	final String creado = "Creado";
    	final String editado = "Editado";
    	
    	
    	iPss.forEach( iPs -> {
    		PuntoShalom ps = mapToPuntoShalom(iPs);
    		if(ps != null) {
	    		String res = creado;
	    		if(puntos.containsKey(ps.getNombre()))
	    			res = editado;
	    		
	    		puntos.put(ps.getNombre(), ps);
	    		results.put(ps.getNombre(), res);
    		}
    	});
    	
    	psRepository.saveAll(puntos.values());
    	
        return results;
    }
    
    /**
     * Activa o desactiva un punto Shalom
     * 
     * @param id ID del punto a modificar
     * @param activo El estado al que pasar el punto
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningun punto
     */
    public void actualizarPunto(Integer id, Boolean activo){
        PuntoShalom ps = buscarPorId(id);
        ps.setActivo(activo);
        psRepository.save(ps);
    }
    
    /**
     * Realiza un eliminado lógico de un punto Shalom
     * 
     * @param id ID del punto a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningun punto
     */
    public void eliminarPunto(Integer id){
    	PuntoShalom ps = buscarPorId(id);
        ps.setActivo(false);
        ps.setEstado(false);
        psRepository.save(ps);
    }
    
    private Map<String, PuntoShalom> cargarDeDB(){
    	return psRepository
    			.listar()
    			.stream()
    			.collect(Collectors.toMap(PuntoShalom::getNombre,Function.identity()));
    }
    
    private PuntoShalom mapToPuntoShalom(Map<String, Object> point) {
    	PuntoShalom ps = new PuntoShalom();
    	
    	ps.setNombre((String)point.get("nombre"));
    	ps.setLugar((String)point.get("lugar"));
    	ps.setProvincia((String)point.get("provincia"));
    	ps.setDireccion((String)point.get("direccion"));
    	ps.setActivo(true);
    	ps.setEstado(true);
    	
    	// Departamento
    	String dpto = (String)point.get("departamento");
    	if(dpto.equals("LIMA")) {
    		// Diferenciar entre lima metropolitana y lima provincias
    		if(ps.getProvincia().equals("LIMA"))
    			return null;
    		else
    			ps.setDepartamento(RegionPeru.LIMA_PROVINCIAS);
    			
    	} else {
    		ps.setDepartamento(RegionPeru.valueOf(dpto.toUpperCase().replace(" ", "_")));
    	}
    	
    	// Distrito
    	String dist = ps.getLugar().split("/")[2];
    	if(dist == null) throw new NullPointerException("Distrito es null: " + ps.getLugar());
    	ps.setDistrito(dist);
    	
    	return ps;
    }
    
    public PuntoShalom buscarPorId(Integer id) throws IdNotFoundException{
		return psRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("puntoshalom"));
	}
}