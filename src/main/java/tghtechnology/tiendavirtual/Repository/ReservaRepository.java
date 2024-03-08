package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Reserva;
import tghtechnology.tiendavirtual.Models.Enums.EstadoReserva;

public interface ReservaRepository extends CrudRepository<Reserva, Integer>{
    
    @Query("SELECT rs From Reserva rs WHERE rs.estado_reserva = 0 OR rs.estado_reserva = 1")
    List<Reserva> listReserva();
    
    @Query("SELECT rs From Reserva rs WHERE rs.estado_reserva = 0 AND id_reserva=:id_res")
    Optional<Reserva> listarUno(@Param("id_res") Integer id_res);
    
    @Query("SELECT rs From Reserva rs WHERE rs.estado_reserva = :estado")
    List<Reserva> listarPorEstado(@Param("estado") EstadoReserva estado);

    @Query("SELECT rs From Reserva rs LEFT JOIN rs.cliente c WHERE rs.estado_reserva = 0 AND c.numero_documento LIKE :documento_identidad")
    boolean existeByDocumentoIdentidad(@Param("documento_identidad") String documento_identidad);
    
    
}
