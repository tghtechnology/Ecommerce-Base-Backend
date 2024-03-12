package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForList;

@Service
@AllArgsConstructor
public class ClienteService {

	@Autowired
    private ClienteRepository cliRepository;

    /**
     * Lista todos los clientes no eliminados
     * @return Una lista de los clientes  en formato ForList
     */
    public List<ClienteDTOForList> listarClientes (){
        List<ClienteDTOForList> clienteList = new ArrayList<>();
        List<Cliente> clis = cliRepository.listarClientes();
        
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
    public ClienteDTOForList listarUno(Integer id){
        Cliente cli= cliRepository.listarUno(id).orElse(null);
        return cli == null ? null : new ClienteDTOForList().from(cli);
    }
    
    /**
     * Registra un nuevo cliente
     * @param iCli Cliente en formato ForInsert
     * @return El cliente creado en formato ForList
     */
    public ClienteDTOForList crearCliente(ClienteDTOForInsert iCli){
        Cliente cli = iCli.toModel();
        cliRepository.save(cli);
        return new ClienteDTOForList().from(cli);
    }
    
    /**
     * Modifica un cliente
     * @param id ID del cliente a modificar
     * @param mCli Datos del cliente en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ningun cliente
     */
    public void actualizarCategoria(Integer id, ClienteDTOForInsert mCli){
        Cliente cli= buscarPorId(id);
        cli = mCli.updateModel(cli);
        cliRepository.save(cli);
    }
    
    /**
     * Realiza un eliminado lógico de una categoría
     * @param id ID de la categoría a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna categoría
     */
    public void eliminarCliente(Integer id){
        Cliente cli = buscarPorId(id);
        cli.setEstado(false);
        cliRepository.save(cli);
    }
    
    
    private Cliente buscarPorId(Integer id) throws IdNotFoundException{
		return cliRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("cliente"));
	}
}