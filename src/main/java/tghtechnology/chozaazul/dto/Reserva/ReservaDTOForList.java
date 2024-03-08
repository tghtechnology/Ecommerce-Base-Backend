package tghtechnology.chozaazul.dto.Reserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Reserva;
import tghtechnology.chozaazul.Models.Enums.EstadoReserva;
import tghtechnology.chozaazul.dto.Cliente.ClienteDTOForList;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ReservaDTOForList {

    private Integer codigo_reserva;
    private ClienteDTOForList cliente;
    private BigDecimal pago_reserva;
    private LocalDate fecha_reserva;
    private LocalTime hora_inicio;
    private LocalTime hora_fin;
    private EstadoReserva estado_reserva;
    private UsuarioDTOForList empleado;


    public ReservaDTOForList(Reserva reserva){
        this.codigo_reserva = reserva.getId_reserva();
        this.cliente = new ClienteDTOForList(reserva.getCliente());
        this.pago_reserva = reserva.getPago_reserva();
        this.fecha_reserva = reserva.getFecha_reserva();
        this.hora_inicio = reserva.getHora_inicio();
        this.estado_reserva = reserva.getEstado_reserva();
        this.empleado = new UsuarioDTOForList(reserva.getUsuario());
    }

    
}