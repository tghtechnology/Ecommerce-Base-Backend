package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Services.ReservaService;
import tghtechnology.tiendavirtual.dto.Reserva.ReservaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Reserva.ReservaDTOForList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/reserva")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;

    @GetMapping    
    public ResponseEntity<List<ReservaDTOForList>> listReservas(){
        List<ReservaDTOForList> lista = reservaService.listarReservas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PostMapping
    public ResponseEntity<ReservaDTOForList> crearReserva(@Valid @RequestBody ReservaDTOForInsert bodyReserva){
        ReservaDTOForList newReserva = reservaService.crearReserva(bodyReserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReserva);
    }
}
