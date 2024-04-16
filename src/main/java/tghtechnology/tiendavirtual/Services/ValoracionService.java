package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.OrdenValoracion;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Valoracion;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Repository.ValoracionRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Valoracion.ValoracionDTOForInsert;
import tghtechnology.tiendavirtual.dto.Valoracion.ValoracionDTOForList;

@Service
@AllArgsConstructor
public class ValoracionService {

    private ValoracionRepository valRepository;
    private ItemRepository itemRepository;
    private ClienteRepository cliRepository;
    private UsuarioRepository userRepository;
    
    private SettingsService settings;

    /**
     * Lista todas las valoraciones de un item según un número específico 
     * de estrellas (opcional), un orden espeífico y una página en particular. <br>
     * Por defecto el orden cronológico es descendente (Nuevas primero).
     * 
     * @param id_item La id del item del cual listar sus valoraciones
     * @param stars El número de estrellas del que se quiere ver las valoraciones.
     * Si no se quiere filtrar por este campo, señalarlo como -1
     * @param orden El orden en el que se quiere entregar las valoraciones. 
     * @param pagina El número de página
     * @return Las valoraciones en formato DTOForList
     * 
     * @throws IdNotFoundException Si la id del item no se corresponde a ninguno.
     * @throws DataMismatchException Si el número de página es menor a 1.
     */
    public List<ValoracionDTOForList> listarPorItem(String id_item, Short stars, OrdenValoracion orden, Integer pagina) {
    	
    	Item item = item_buscarPorTextId(id_item);
    	if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
    	
    	List<Order> orders = new ArrayList<>();
    	if(orden != OrdenValoracion.RECENT) {
    		orders.add(new Order(orden == OrdenValoracion.BEST ? Sort.Direction.DESC : Sort.Direction.ASC, "estrellas"));
    	}
    	orders.add(new Order(Sort.Direction.DESC, "id_valoracion"));
    	Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.items"), Sort.by(orders));
    	
    	List<Valoracion> vals = valRepository.listarPorItem(item, stars, pag);
    	
    	return vals.stream().map(val -> new ValoracionDTOForList().from(val)).collect(Collectors.toList());
    }
    
    /**
     * Lista todas las valoraciones de un cliente desde las más nuevas
     * hasta las más antiguas además de un orden en específico y una página
     * en particular.
     * 
     * @param id_cliente la ID del cliente del cual buscar sus valoraciones.
     * @param orden El orden en el que se ordenarán las valoraciones.
     * @param pagina El número de página.
     * @return Las valoraciones en formato DTOForList.
     * 
     * @throws IdNotFoundException Si la id del cliente no se corresponde a ninguno.
     * @throws DataMismatchException Si el número de página es menor a 1.
     */
    public List<ValoracionDTOForList> listarPorCliente(Integer id_usuario, OrdenValoracion orden, Integer pagina, Authentication auth) {
    	
    	Usuario user = user_buscarPorId(id_usuario);
    	if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
    	
    	if(!checkPermitted(user, auth))
    		throw new AccessDeniedException("");
    	
    	List<Order> orders = new ArrayList<>();
    	if(orden != OrdenValoracion.RECENT) {
    		orders.add(new Order(orden == OrdenValoracion.BEST ? Sort.Direction.DESC : Sort.Direction.ASC, "estrellas"));
    	}
    	orders.add(new Order(Sort.Direction.DESC, "id_valoracion"));
    	Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.valoracion"), Sort.by(orders));
    	
    	List<Valoracion> vals = valRepository.listarPorUsuario(user, pag);
    	
    	return vals.stream().map(val -> new ValoracionDTOForList().from(val)).collect(Collectors.toList());
    }
    
    public ValoracionDTOForList listarUno(Integer id_valoracion, Authentication auth) {
    	Valoracion val = buscarPorId(id_valoracion);
    	
    	if(!checkPermitted(val.getUsuario(), auth))
    		throw new AccessDeniedException("");
    	
    	return new ValoracionDTOForList().from(val);
    }
    
    /**
     * Crea una nueva valoración para un Item según la autenticación del usuario.<br>
     * También actualiza el conteo de valoraciones y promedio de estrellas del item
     * @param iVal La valoración en formato ForInsert.
     * @param auth La utenticación del usuario.
     * @return La valoración creada en formato ForList.
     */
    @Transactional
    public ValoracionDTOForList crearValoracion(ValoracionDTOForInsert iVal, Authentication auth) {
    	
    	Usuario user = user_buscarPorUsername(auth.getName());
    	Item item = item_buscarPorId(iVal.getId_item());
    	
    	if(valRepository.listarUno(item, user).isPresent())
    		throw new DataIntegrityViolationException("El usuario ya tiene una valoracion en ese item");
    	
    	Valoracion val = iVal.toModel();
    	val.setItem(item);
    	val.setUsuario(user);
    	val = valRepository.save(val);
    	
    	// Actualizar el promedio de valoraciones del item
    	item.setEstrellas(sumarEstrellas(item, val.getEstrellas()));
    	item.setValoraciones(item.getValoraciones() + 1);
    	itemRepository.save(item);
    	
    	return new ValoracionDTOForList().from(val);
    }
    
    /**
     * Modifica una valoración ya existente. Solo lo puede hacer el cliente mismo o un gerente
     * @param id_valoracion La ID de la valoración a modificar
     * @param mVal La valoración en formato ForInsert
     * @param auth la autorización
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public void modificarValoracion(Integer id_valoracion, ValoracionDTOForInsert mVal, Authentication auth) {

    	Valoracion val = buscarPorId(id_valoracion);
    	Item item = val.getItem();
    	
    	final short _old = val.getEstrellas();
    	final short _new = mVal.getEstrellas();
    	
    	if(!auth.getName().equals(val.getUsuario().getUsername()))
    		throw new AccessDeniedException("");
    	
    	val = mVal.updateModel(val);
    	valRepository.save(val);
    	
    	// Actualizar el promedio de valoraciones del item
    	item.setEstrellas(modificarEstrellas(item, _old, _new));
    	itemRepository.save(item);
    }
    
    /**
     * Elimina una valoración.<br>
     * Solo lo puede hacer el mismo cliente que la registró en primer lugar.     
     * @param id_valoracion
     * @param auth
     */
    public void eliminarValoracion(Integer id_valoracion, Authentication auth) {
    	Usuario user = user_buscarPorUsername(auth.getName());
    	Valoracion val = buscarPorId(id_valoracion);
    	Item item = val.getItem();
    	
    	if(!checkPermitted(user, auth))
    		throw new AccessDeniedException("");
    	
    	val.setEstado(false);
    	valRepository.save(val);
    	
    	
    	// Actualizar el promedio de valoraciones del item
    	item.setEstrellas(modificarEstrellas(item, val.getEstrellas(), (short)0));
    	item.setValoraciones(item.getValoraciones() - 1);
    	itemRepository.save(item);
    }
    
    private Double sumarEstrellas(Item item, Short nuevasEstrellas) {
    	Double x = item.getValoraciones().doubleValue();
    	Double n = item.getEstrellas();
    	Double v = nuevasEstrellas.doubleValue();
    	
    	return x == 0
    			? (v) // Si el item tenía 0 valoraciones, devolver el valor entrante
    			: ((n + v/x)/(1 + 1/x));
    }
    
    private Double modificarEstrellas(Item item, Short antiguasEstrellas, Short nuevasEstrellas) {
    	Double x = item.getValoraciones().doubleValue();
    	Double n = item.getEstrellas();
    	Double v = nuevasEstrellas.doubleValue();
    	Double w = antiguasEstrellas.doubleValue();
    	
    	return n + (v - w)/x;
    }
    
    private boolean checkPermitted(Usuario user, Authentication auth) {
    	return auth.getName().equals(user.getUsername()) ||
    		   TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE);
    }
    
    public Cliente cli_buscarPorId(Integer id) throws IdNotFoundException{
		return cliRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("cliente"));
	}
    
    public Item item_buscarPorTextId(String text_id) throws IdNotFoundException{
		return itemRepository.listarUno(text_id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
    public Item item_buscarPorId(Integer id) throws IdNotFoundException{
		return itemRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
    
    public Valoracion buscarPorId(Integer id) throws IdNotFoundException{
		return valRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("valoracion"));
	}
    
    private Usuario user_buscarPorId(Integer id) throws IdNotFoundException{
		return userRepository.listarUno(id).orElseThrow( () -> new AccessDeniedException(""));
	}
    
    private Usuario user_buscarPorUsername(String username) {
		return userRepository.listarPorUserName(username).orElseThrow( () -> new IdNotFoundException("usuario"));
	}
}