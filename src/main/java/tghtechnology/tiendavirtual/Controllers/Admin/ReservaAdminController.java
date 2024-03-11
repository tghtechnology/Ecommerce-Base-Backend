package tghtechnology.tiendavirtual.Controllers.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Enums.EstadoReserva;
import tghtechnology.tiendavirtual.Services.ReservaService;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.dto.Reserva.ReservaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Reserva.ReservaDTOForList;


@RestController
@RequestMapping("/admin/reserva")
public class ReservaAdminController {
    
    @Autowired
    private ReservaService reservaService;

    @Empleado
    @GetMapping    
    public ResponseEntity<List<ReservaDTOForList>> listReservas(){
            List<ReservaDTOForList> lista = reservaService.listarReservas();
            return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @Empleado
    @PostMapping
    public ResponseEntity<ReservaDTOForList> crearReserva(@Valid @RequestBody ReservaDTOForInsert bodyReserva){
            ReservaDTOForList newReserva = reservaService.crearReserva(bodyReserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReserva);
    }
    
    @Empleado
	@PutMapping("/{id}/{estado}")
    public ResponseEntity<Void> modificarEstadoReserva(@PathVariable Integer id, @PathVariable EstadoReserva estado){
        reservaService.modificarEstadoReserva(id, estado);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
    //TODO: modificar
}
