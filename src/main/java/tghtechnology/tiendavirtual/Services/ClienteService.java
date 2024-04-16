package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.PersonaRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForList;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForModify;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository cliRepository;
	private PersonaRepository perRepository;
	private UsuarioRepository userRepository;
	
	private UsuarioService userService;
	private SettingsService settings;

    /**
     * Lista todos los clientes no eliminados
     * @return Una lista de los clientes  en formato ForList
     */
    public List<ClienteDTOForList> listarClientes (Integer pagina){
        List<ClienteDTOForList> clienteList = new ArrayList<>();
        
        if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
        
        Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.cliente"));
        List<Cliente> clis = cliRepository.listarClientes(pag);
        
        clis.forEach( x -> {
            clienteList.add(new ClienteDTOForList().from(x));
        });
        return clienteList;
    }
    
    /**
     * Obtiene un cliente en específico según su ID
     * @param id la ID del cliente
     * @return el cliente encontrado en formato ForList o null si no existe
     */
    public ClienteDTOForList listarUno(Integer id, Authentication auth){
        Cliente cli= cliRepository.listarUno(id).orElse(null);
        
        // No permite modificar un cliente si no es el mismo quien lo hace
        // o si tiene permisos suficientes
        if(cli == null || !checkPermitted(cli, auth))
    		throw new AccessDeniedException("");
        
        return cli == null ? null : new ClienteDTOForList().from(cli);
    }

    /**
     * Registra un nuevo cliente.
     * @param iCli El cliente a registrar en formato DTOForInsert.
     * @return El empleado creado en formato DTOForList.
     * @throws DataMismatchException Si ambos campos de identificación de persona son nulos.
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public ClienteDTOForList crearCliente(ClienteDTOForInsert iCli){
    	
    	if(userRepository.listUser().isEmpty()) {
  			throw new BadCredentialsException(null);
  		}
    	
    	Persona per = perRepository.save(iCli.getPersona().toModel());
        
    	Cliente cli = iCli.toModel();
    	cli.setPersona(per);
    	cli.setId_persona(per.getId_persona());
    	
    	iCli.getUsuario().setId_persona(per.getId_persona());
    	iCli.getUsuario().setCargo(TipoUsuario.CLIENTE);
    	Usuario user = userService.crearUsuarioPorInstancia(iCli.getUsuario(), per);
    	
        cli.setUsuario(user);
        cli = cliRepository.save(cli);
        
        return new ClienteDTOForList().from(cli);
    } 
    
    /**
     * Actualiza los datos de un cliente.<br>
     * Se pueden actualizar también sus credenciales si el cliente tiene el campo credenciales.
     * @param id ID del cliente a actualizar.
     * @param mCli Datos del cliente en formato DTOForInsert.
     * @param auth La instancia de autenticación del cliente/empleado que realiza la operación
     * @throws DataMismatchException Si el campo persona es nulo.
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public void actualizarCliente(Integer id, ClienteDTOForModify mCli, Authentication auth){
        Cliente cliente = buscarPorId(id);        
        
        // No permite modificar un cliente si no es el mismo quien lo hace
        // o si tiene permisos suficientes
        if(cliente == null || !checkPermitted(cliente, auth))
    		throw new AccessDeniedException("");
        
        cliente = mCli.updateModel(cliente);
        
        perRepository.save(cliente.getPersona());
        
        if(mCli.getCredenciales() != null) {
        	userService.actualizarUsuarioCliente(cliente.getUsuario(), mCli.getCredenciales(), auth);
        }
        
        cliRepository.save(cliente);
    }
    
    /**
     * Elimina un cliente.
     * @param id La ID del cliente a eliminar.
     * @param auth La instancia de autenticación del cliente/empleado que realiza la operación
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    public void eliminarCliente(Integer id, Authentication auth){
        Cliente cliente = buscarPorId(id);
        
        // No permite eliminar un usuario con rol superior
        if(cliente == null || !checkPermitted(cliente, auth))
    		throw new AccessDeniedException("");
        
        cliente.setRecibe_correos(false);
        cliente.setEstado(false);
        cliente = cliRepository.save(cliente);
        
        userService.eliminarUsuario(cliente.getUsuario().getId_usuario());
        
    }
    
    private boolean checkPermitted(Cliente cli, Authentication auth) {
    	return auth.getName().equals(cli.getUsuario().getUsername()) ||
    		   TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE);
    }
    
    private Cliente buscarPorId(Integer id) {
		return cliRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("cliente"));
	}
    
}