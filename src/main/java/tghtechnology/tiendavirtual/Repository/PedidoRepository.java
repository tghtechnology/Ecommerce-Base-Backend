package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Pedido;
public interface PedidoRepository extends CrudRepository<Pedido, Integer>{

	@Query("SELECT p FROM Pedido p WHERE p.estado = true")
    List<Pedido> listarPedido();

    @Query("SELECT p FROM Pedido p WHERE p.estado = true AND p.id_pedido = :ped_id")
    Optional<Pedido> listarUno(@Param("ped_id") Integer idPedido);
    
}
