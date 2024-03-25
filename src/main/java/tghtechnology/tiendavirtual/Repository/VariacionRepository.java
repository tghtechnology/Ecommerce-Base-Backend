package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Variacion;

public interface VariacionRepository extends CrudRepository<Variacion, Integer>{

	@Query("SELECT v FROM Variacion v WHERE v.estado = true AND v.item = :itm")
    List<Variacion> listarPorItem(@Param("itm") Item item);
	
    @Query("SELECT v FROM Variacion v WHERE v.estado = true AND v.id_variacion = :id_var")
    Optional<Variacion> listarUno(@Param("id_var") Integer id_variacion);
}
