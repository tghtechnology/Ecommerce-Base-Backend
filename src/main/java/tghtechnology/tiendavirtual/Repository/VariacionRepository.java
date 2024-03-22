package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.VariacionItem;

public interface VariacionRepository extends CrudRepository<VariacionItem, Integer>{

	@Query("SELECT v FROM VariacionItem v WHERE v.estado = true AND v.item = :itm")
    List<VariacionItem> listarPorItem(@Param("itm") Item item);
	
    @Query("SELECT v FROM Variacion v WHERE v.estado = true AND v.id_variacion = :id_var")
    Optional<VariacionItem> listarUno(@Param("id_var") Integer id_variacion);
}
