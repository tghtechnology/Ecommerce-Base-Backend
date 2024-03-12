package tghtechnology.tiendavirtual.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Descuento;


public interface DescuentoRepository extends CrudRepository<Descuento, Integer>{
    
	@Query("SELECT dsc FROM Descuento dsc WHERE dsc.estado = true")
    List<Descuento> listarDescuentos();
	
	@Query("SELECT dsc FROM Descuento dsc "
			+ "LEFT JOIN dsc.item AS itm "
			+ "WHERE dsc.estado = true "
			+ "AND dsc.activo =:est"
			+ "AND itm.id_item =:id_item"
		)
    List<Descuento> listarExt(
    		@Param("est") boolean est,
    		@Param("id_item") Integer id_item
    	);
	
}