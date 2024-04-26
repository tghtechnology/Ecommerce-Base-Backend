package tghtechnology.tiendavirtual.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Venta;
public interface VentaRepository extends JpaRepository<Venta, Integer>{

	@Query("SELECT v FROM Venta v WHERE v.estado = true")
    List<Venta> listar(Pageable pageable);
	
	@Query("SELECT v FROM Venta v WHERE v.estado = true AND v.cliente = :cli")
    List<Venta> listarPorCliente(@Param("cli") Cliente cli, Pageable pageable);

    @Query("SELECT v FROM Venta v WHERE v.estado = true AND v.id_venta = :ven_id")
    Optional<Venta> listarUno(@Param("ven_id") Integer idVenta);
    
    List<Venta> findAllByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    
}
