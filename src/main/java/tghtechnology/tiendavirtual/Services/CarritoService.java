package tghtechnology.tiendavirtual.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Carrito;
import tghtechnology.tiendavirtual.Models.DetalleCarrito;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Repository.DetalleCarritoRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Carrito.CarritoDTOForList;
import tghtechnology.tiendavirtual.dto.Carrito.DetalleCarrito.DetalleCarritoDTOForInsert;

@Service
@AllArgsConstructor
public class CarritoService {

	private VariacionRepository varRepository;
	private DetalleCarritoRepository dcRepository;
	private UsuarioRepository userRepository;
    
    /**
     * Obtiene un carrito en específico según su ID de usuario
     * @param id la ID del usuario
     * @param auth La autenticación del usuario
     * @return el carrito encontrado
     */
    public CarritoDTOForList listarUno(Authentication auth){
    	
    	Usuario user = user_buscarPorId(auth.getName());
    	Carrito car = user.getCarrito();
        
        // No permite listar el carrito de un usuario a menos que sea suyo
        // o si tiene permisos suficientes
        if(!checkPermitted(user, auth))
    		throw new AccessDeniedException("");
        
        return new CarritoDTOForList().from(car);
    }

    /**
     * Añade un detalle al carrito
     * @param id_usuario La id del usuario al que perteneceel carrito.
     * @param iDet detalle del carrito en formato DTOForInsert
     * @param auth La autenticación del usuario
     * @throws IdNotFoundException Si una ID no se corresponde con ningún usuario o variación.
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public void addDetalle(DetalleCarritoDTOForInsert iDet, Authentication auth){
    	
    	Usuario user = user_buscarPorId(auth.getName());
    	if(user.getCarrito() == null)
    		throw new AccessDeniedException("Usuario no tiene carrito");
    	
    	// No permite modificar el carrito de un usuario a menos que sea suyo
        // o si tiene permisos suficientes
        if(!checkPermitted(user, auth))
    		throw new AccessDeniedException("");
    	
        DetalleCarrito dc = iDet.toModel();
        dc.setCarrito(user.getCarrito());
        dc.setCorrelativo(user.getCarrito().getDetalles().size()+1);
        
        Variacion var = var_buscarPorId(iDet.getId_variacion());
        dc.setVariacion(var);
        
        dcRepository.save(dc);
    } 
    
    /**
     * Actualiza un detalle del carrito.<br>
     * Solo permite cambiar la cantidad.
     * @param id ID del detalle a actualizar.
     * @param mDet El detalle a modificar en formato ForInsert
     * @param auth La autenticación del usuario
     * @throws IdNotFoundException Si la ID no se corresponde con ningun detalle.
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public void actualizarDetalleCarrito(Integer corr, DetalleCarritoDTOForInsert mDet, Authentication auth){

    	//Si la cantidad es 0, eliminar el detalle
    	if(mDet.getCantidad() == 0) { eliminarDetalle(corr, auth); return; }
    	
    	Usuario user = user_buscarPorId(auth.getName());
    	if(user.getCarrito() == null)
    		throw new AccessDeniedException("Usuario no tiene carrito");
    	DetalleCarrito dc = buscarPorCorrelativo(user, corr);
  
    	// No permite modificar el carrito de un usuario a menos que sea suyo
        // o si tiene permisos suficientes
        if(!dc.getCarrito().getUsuario().isEnabled() || !checkPermitted(dc.getCarrito().getUsuario(), auth))
    		throw new AccessDeniedException("");
    	
    	dc.setCantidad(mDet.getCantidad());
    	dcRepository.save(dc);
    	
    }
    
    /**
     * Elimina un detalle.
     * @param id La ID del detalle a eliminar.
     * @param auth La autenticación de usuario
     * @throws IdNotFoundException Si la ID no se corresponde con ningun detalle.
     */
    public void eliminarDetalle(Integer corr, Authentication auth){
    	
    	Usuario user = user_buscarPorId(auth.getName());
    	if(user.getCarrito() == null)
    		throw new AccessDeniedException("Usuario no tiene carrito");
    	DetalleCarrito dc = buscarPorCorrelativo(user, corr);
  
    	// No permite modificar el carrito de un usuario a menos que sea suyo
        // o si tiene permisos suficientes
        if(!dc.getCarrito().getUsuario().isEnabled() || !checkPermitted(dc.getCarrito().getUsuario(), auth))
    		throw new AccessDeniedException("");
        
    	dcRepository.delete(dc);
    	
    	List<DetalleCarrito> dcs = dc.getCarrito()
										.getDetalles()
										.stream()
										.filter( _dc -> _dc.getCorrelativo() > dc.getCorrelativo())
										.collect(Collectors.toList());
    	
    	dcs.forEach( _dc -> _dc.setCorrelativo(_dc.getCorrelativo()-1));
    	dcRepository.saveAll(dcs);
    }
    
    private boolean checkPermitted(Usuario user, Authentication auth) {
    	return auth.getName().equals(user.getUsername()) ||
    		   TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE);
    }
    
    private DetalleCarrito buscarPorCorrelativo(Usuario user, Integer corr) {
    	
    	Carrito car = user.getCarrito();
    	
    	return car.getDetalles()
					.stream()
					.filter(_dc -> _dc.getCorrelativo() == corr)
					.findFirst()
					.orElseThrow(() -> new IdNotFoundException("detalle"));
    }
    
    private Usuario user_buscarPorId(String username) {
		return userRepository.listarPorUserName(username).orElseThrow( () -> new IdNotFoundException("usuario"));
	}
    
    private Variacion var_buscarPorId(Integer id) {
		return varRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
}