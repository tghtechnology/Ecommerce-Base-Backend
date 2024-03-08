package tghtechnology.tiendavirtual.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Enums.TipoPlato;

public interface PlatoRepository extends CrudRepository<Item, Integer>{

	@Query("SELECT pl FROM Item pl WHERE pl.estado = true")
    List<Item> listarPlato();

	/**
	 * Función para buscar platos siguiendo distintos filtros
	 * 
	 * @param query Texto para buscar en el nombre o descripción del plato
	 * @param min Precio mínimo para considerar
	 * @param max Precio máximo para considerar
	 * @param catId ID de texto de la categoría
	 * @param tipoPlatoAsInteger Enumerador TipoPlato (NINGUNO para no filtrar)
	 * @return
	 */
    @Query("SELECT p FROM Item p "
    		+ "LEFT JOIN p.categoria AS c "
    		+ "WHERE "
    		+ "(p.nombre_plato LIKE %:query% "
    		+ "OR p.descripcion LIKE %:query%) "
    		+ "AND p.precio > :min "
    		+ "AND p.precio < :max "
    		+ "AND (:catId LIKE '' OR c.text_id = :catId)"
    		+ "AND (:tipoPlato = 0 OR p.tipoPlato = :tipoPlato)"
    		+ "AND p.estado=true")
    List<Item> listar(@Param("query") String query,
    					  @Param("min") BigDecimal min,
    					  @Param("max") BigDecimal max,
    					  @Param("catId") String catId,
    					  @Param("tipoPlato") TipoPlato tipoPlato);
	
	
    @Query("SELECT pl FROM Item pl WHERE pl.estado = true AND pl.id_plato = :pla_id")
    Optional<Item> listarUno(@Param("pla_id") Integer idPlato);
    
    @Query("SELECT pl FROM Item pl WHERE pl.estado = true AND pl.text_id = :text_id")
    Optional<Item> listarUno(@Param("text_id") String text_id);
    
    //Optional<Item> findByText_id(@Param("text_id") String textId);
}
