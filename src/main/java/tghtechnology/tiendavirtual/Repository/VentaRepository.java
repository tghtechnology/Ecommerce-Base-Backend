package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Venta;
public interface VentaRepository extends CrudRepository<Venta, Integer>{

	@Query("SELECT v FROM Venta v WHERE v.estado = true")
    List<Venta> listarPedido();

    @Query("SELECT v FROM Venta v WHERE v.estado = true AND v.id_venta = :ven_id")
    Optional<Venta> listarUno(@Param("ven_id") Integer idVenta);
    
}
