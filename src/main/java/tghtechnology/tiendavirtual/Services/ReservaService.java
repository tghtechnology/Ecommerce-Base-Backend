package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Reserva;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Enums.EstadoReserva;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.ReservaRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.tiendavirtual.dto.Reserva.ReservaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Reserva.ReservaDTOForList;

@Service
@AllArgsConstructor
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    private UsuarioRepository userRepository;
    private ClienteRepository cliRepository;

    /*Listar Reservas */
    public List<ReservaDTOForList> listarReservas() {

        List<ReservaDTOForList> lista = new ArrayList<>();
        List<Reserva> reserva = (List<Reserva>) reservaRepository.listReserva();

        reserva.forEach(r -> {
            lista.add(new ReservaDTOForList(r));
        });

        return lista;

    }

    /*Crear Nueva Reserva */
    public ReservaDTOForList crearReserva(ReservaDTOForInsert inputReserva){
        Reserva newReserva = new Reserva();

        newReserva.setPago_reserva(inputReserva.getPago_reserva());
        newReserva.setFecha_reserva(inputReserva.getFecha_reserva());
        newReserva.setHora_inicio(inputReserva.getHora_inicio());

        Usuario user = userRepository.listarUno(inputReserva.getEmpleado_encargado()).orElseThrow(()-> new IdNotFoundException("usuario"));
        newReserva.setUsuario(user);
        newReserva.setEstado_reserva(EstadoReserva.ACTIVA);

        ClienteDTOForInsert iCli = inputReserva.getCliente();
        Cliente cliente = new Cliente();
        
        cliente.setNombre(iCli.getNombre());
        cliente.setTelefono(iCli.getTelefono());
        cliente.setTipo_documento(iCli.getTipo_documento());
        cliente.setNumero_documento(iCli.getNumero_documento());
        cliente.setCorreo(iCli.getEmail());
        cliente.setDistrito(iCli.getDistrito());
        cliente.setDireccion(iCli.getDireccion());
        cliente.setReferencia(iCli.getReferencia());
        
        cliente = cliRepository.save(cliente);
        newReserva.setCliente(cliente);
        
        reservaRepository.save(newReserva);

        return new ReservaDTOForList(newReserva);
    }
    
	// Modificar estado de la reserva
	public void modificarEstadoReserva(Integer id, EstadoReserva est) {
		// Buscar el pedido a modificar
		Reserva res = buscarPorId(id);
		//modificar su estado
		res.setEstado_reserva(est);
		res = reservaRepository.save(res);
		
	}
	
	private Reserva buscarPorId(Integer id) {
		return reservaRepository.listarUno(id).orElseThrow(() -> new IdNotFoundException("erserva"));
	}
    
}
