package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Rebaja;
import tghtechnology.tiendavirtual.Repository.CategoriaRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.RebajaRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Rebaja.RebajaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Rebaja.RebajaDTOForList;
import tghtechnology.tiendavirtual.dto.Rebaja.RebajaDTOForListFull;

@Service
@AllArgsConstructor
public class RebajaService {

    private RebajaRepository rebRepository;
    private CategoriaRepository catRepository;
    private ItemRepository itemRepository;
    
    /**
     * Lista todas las rebajas activas (No eventos).<br>
     * Si el solicitante es EMPLEADO o superior, devuelve también
     * las rebajas desactivadas (no eliminadas).
     * 
     * @param auth La autenticación del usuario.
     * 
     * @return Una lista de las rebajas en formato ForList.
     */
    public List<RebajaDTOForList> listarRebajas (Authentication auth){
        List<RebajaDTOForList> rebajaList = new ArrayList<>();
        List<Rebaja> rebs = rebRepository.listarRebajas(checkPermission(auth));
        
        rebs.forEach( x -> {
            rebajaList.add(new RebajaDTOForList().from(x));
        });
        return rebajaList;
    }
    
    /**
     * Obtiene una rebaja en específico según su ID.<br>
     * Ignora si es un evento o no.<br>
     * Si la rebaja está desactivada, sólo la podran ver los que tengan rol EMPLEADO,
     * 
     * @param id La ID de la rebaja
     * @param auth La autenticación del usuario
     * 
     * @return la rebaja encontrada en formato ForList o null si no existe o si está
     * desactivada y el usuario no tiene permisos.
     */
    public RebajaDTOForListFull listarUno(Integer id, Authentication auth){
        Rebaja rebaja = rebRepository.listarUno(id).orElse(null);
        if(rebaja != null && !rebaja.getActivo() && !checkPermission(auth))
        	rebaja = null;
        
        return rebaja == null ? null : new RebajaDTOForListFull().from(rebaja);
    }
    
    /**
     * Registra una nueva rebaja
     * 
     * @param iReb La rebaja en formato ForInsert
     * @return La rebaja creada en formato ForListFull
     */
    @Transactional
    public RebajaDTOForList crearRebaja(RebajaDTOForInsert iReb){
    	
    	// Comprobar que no exista un evento si es que se quiere insertar uno
    	if(iReb.getEs_evento()) {
    		if(rebRepository.listarEvento().isPresent())
    			throw new DataIntegrityViolationException("Ya existe un evento activo.");
    	}
    	
    	// Obtener las categorias a añadir
    	final List<Categoria> cats = iReb.getCategorias()
    							.stream()
    							.map(cc -> cat_buscarPorId(cc))
    							.collect(Collectors.toList());
    	// Obtener los items a añadir
    	final List<Item> items = iReb.getItems()
								.stream()
								.map(ii -> item_buscarPorId(ii))
								.collect(Collectors.toList());

    	Rebaja reb = iReb.toModel();
        
        // Añadir items y categorias
        reb.getCategorias().addAll(cats);
        reb.getItems().addAll(items);
        
        reb = rebRepository.save(reb);
        
        return new RebajaDTOForList().from(reb);
    }
    
    /**
     * Modifica una rebaja.
     * 
     * @param id ID de la rebaja a modificar
     * @param mReb Datos de la rebaja en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna rebaja
     */
    public void actualizarCategoria(Integer id, RebajaDTOForInsert mReb){
        Rebaja reb = buscarPorId(id);
        reb = mReb.updateModel(reb);
        
        reb.setCategorias(mReb.getCategorias()
							.stream()
							.map(cc -> cat_buscarPorId(cc))
							.collect(Collectors.toSet()));
        
        reb.setItems(mReb.getItems()
							.stream()
							.map(ii -> item_buscarPorId(ii))
							.collect(Collectors.toSet()));
        
        rebRepository.save(reb);
    }
    
    /**
     * Realiza un eliminado lógico de una rebaja
     * 
     * @param id ID de la rebaja a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna rebaja
     */
    public void eliminarRebaja(Integer id){
        Rebaja reb = buscarPorId(id);
        reb.setEstado(false);
        reb.setActivo(false);
        rebRepository.save(reb);
    }
    
    
    /**
     * Cambia el estado ACTIVO de una rebaja/evento
     * @param id La ID de la rebaja a modificar
     * @param activo Si se quiere activar o desactivar la rebaja
     */
    public void cambiarEstadoRebaja(Integer id, boolean activo) {
    	Rebaja reb = buscarPorId(id);
    	
    	if(activo && reb.getEs_evento())
    		if(rebRepository.listarEvento().isPresent())
    			throw new DataIntegrityViolationException("Ya existe un evento activo.");
    	
        reb.setActivo(activo);
        rebRepository.save(reb);
    }
    
    /*
     * EVENTO:
     * Los eventos son iguales a las rebajas excepto por un factor:
     * Solo puede haber uno.
     */
    
    /**
     * Lista todos los eventos (No rebajas).
     * 
     * @return Una lista de las rebajas en formato ForList.
     */
    public List<RebajaDTOForList> listarEventos (){
        List<Rebaja> rebs = rebRepository.listarEventos();
        
        return rebs.stream()
        		.map(r -> new RebajaDTOForList().from(r))
        		.collect(Collectors.toList());
    }
    
    /**
     * Lista el evento activo del momento.
     * 
     * @return El evento en formato DTOForList
     */
    public RebajaDTOForList listarEvento() {
    	Rebaja reb = rebRepository.listarEvento().orElse(null);
    	
    	return new RebajaDTOForList().from(reb);
    }
    
    
    
    private boolean checkPermission(Authentication auth) {
    	return (auth != null 
				&& auth.getAuthorities() != null
				&& TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.EMPLEADO));
    }
    
    private Item item_buscarPorId(String text_id) {
		return itemRepository.listarUno(text_id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
    public Categoria cat_buscarPorId(String text_id) throws IdNotFoundException{
		return catRepository.listarUno(text_id).orElseThrow( () -> new IdNotFoundException("categoria"));
	}
    
    public Rebaja buscarPorId(Integer id) throws IdNotFoundException{
		return rebRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("rebaja"));
	}
}