package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Direccion;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.DireccionRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Direccion.DireccionDTOForInsert;
import tghtechnology.tiendavirtual.dto.Direccion.DireccionDTOForList;

@Service
@AllArgsConstructor
public class DireccionService {

	@Autowired
    private DireccionRepository dirRepository;
	private ClienteRepository cliRepository;

    /**
     * Lista todas las direcciones no eliminadas de un cliente
     * @param id_cliente la ID del cliente del cual buscar las direcciones
     * @param auth La autenticación del usuario
     * @return Una lista de las direcciones en formato ForList
     * @throws AccessDeniedException Si se intenta listar una dirección de un cliente que no existe o
     * del que no se tiene permisos
     */
    public List<DireccionDTOForList> listarDirecciones (Integer id_cliente, Authentication auth){
    	
    	Cliente cli = cliRepository.listarUno(id_cliente).orElse(null);
    	
    	if(cli == null || !checkPermitted(cli, auth))
    		throw new AccessDeniedException("");
    	
        List<DireccionDTOForList> dirList = new ArrayList<>();
        List<Direccion> dirs = (List<Direccion>) dirRepository.listar(cli);
        
        dirs.forEach( x -> {
            dirList.add(new DireccionDTOForList().from(x));
        });
        return dirList;
    }
    
    /**
     * Obtiene una direccion en específico según su ID
     * @param id la ID de la dirección
     * @param auth La autenticación del usuario
     * @return la dirección encontrada en formato ForList
     * @throws AccessDeniedException Si se intenta listar una dirección que no existe o que no se tiene permiso
     */
    public DireccionDTOForList listarUno(Integer id, Authentication auth){
    	
    	Direccion dir = dirRepository.listarUno(id).orElse(null);
    	
    	if(dir == null || !checkPermitted(dir.getCliente(), auth))
    		throw new AccessDeniedException("");
    	
        return new DireccionDTOForList().from(dir);
    }
    
    /**
     * Registra una nueva dirección
     * @param iDir Dirección en formato ForInsert
     * @param id_cliente ID del cliente al cual asignar la dirección
     * @param auth La autenticación del usuario
     * @return la dirección creada en formato ForList
     * @throws AccessDeniedException Si se intenta asignar una dirección a un cliente que no existe o
     * del que no se tiene permisos
     */
    public DireccionDTOForList crearDireccion(DireccionDTOForInsert iDir, Authentication auth){

    	if(iDir.getId_cliente() == null)
    		throw new DataMismatchException("id_cliente", "El campo no puede ser nulo");
    	
    	Cliente cli = cliRepository.listarUno(iDir.getId_cliente()).orElse(null);
    	
    	if(cli == null || !checkPermitted(cli, auth))
    		throw new AccessDeniedException("");
    	
    	Direccion dir = iDir.toModel();
    	dir.setCliente(cli);
    	dir = dirRepository.save(dir);
    	
    	return new DireccionDTOForList().from(dir);
    }
    
    /**
     * Modifica una dirección
     * @param id ID de la dirección a modificar
     * @param mDir Datos de la dirección en formato ForInsert
     * @param auth La autenticación del usuario
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna dirección
     * @throws AccessDeniedException Si se intenta actualizar una dirección que no existe o que no se tiene permiso
     */
    public void actualizarDireccion(Integer id, DireccionDTOForInsert mDir, Authentication auth){
    	
    	Direccion dir = dirRepository.listarUno(id).orElse(null);
    	
    	if(dir == null || !checkPermitted(dir.getCliente(), auth))
    		throw new AccessDeniedException("");
    	
    	dir = mDir.updateModel(dir);
    	dirRepository.save(dir);
    }
    
    /**
     * Realiza un eliminado lógico de una dirección
     * @param id ID de la dirección a eliminar
     * @param auth La autenticación del usuario
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna dirección
     * @throws AccessDeniedException Si se intenta eliminar una dirección que no existe o que no se tiene permiso
     */
    public void eliminarDireccion(Integer id, Authentication auth){
    	
    	Direccion dir = dirRepository.listarUno(id).orElse(null);
    	
    	if(dir == null || !checkPermitted(dir.getCliente(), auth))
    		throw new AccessDeniedException("");
    	
    	dir.setEstado(false);
    	dirRepository.save(dir);
    }
    
    private boolean checkPermitted(Cliente cli, Authentication auth) {
    	return auth.getName().equals(cli.getUsuario().getUsername()) ||
    		   TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE);
    }
    
}