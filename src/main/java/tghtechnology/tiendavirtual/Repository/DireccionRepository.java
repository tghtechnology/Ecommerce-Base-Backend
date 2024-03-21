package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Direccion;

@Repository
public interface DireccionRepository extends CrudRepository<Direccion, Integer>{

	@Query("SELECT dir FROM Direccion dir WHERE dir.estado = true AND dir.cliente = :cli")
    List<Direccion> listar(@Param("cli") Cliente cliente);

    @Query("SELECT dir FROM Direccion dir WHERE dir.estado = true AND dir.id_direccion = :dir_id")
    Optional<Direccion> listarUno(@Param("dir_id") Integer id_direccion);
}
