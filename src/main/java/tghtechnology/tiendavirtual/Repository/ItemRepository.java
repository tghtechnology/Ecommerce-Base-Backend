package tghtechnology.tiendavirtual.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Item;

public interface ItemRepository extends CrudRepository<Item, Integer>{

	@Query("SELECT i FROM Item i WHERE i.estado = true")
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
    @Query("SELECT i FROM Item i "
    		+ "LEFT JOIN i.categoria AS c "
    		+ "WHERE "
    		+ "(i.nombre_plato LIKE %:query% "
    		+ "OR i.descripcion LIKE %:query%) "
    		+ "AND i.precio > :min "
    		+ "AND i.precio < :max "
    		+ "AND (:catId LIKE '' OR c.text_id = :catId)"
    		+ "AND i.estado=true")
    List<Item> listar(@Param("query") String query,
    					  @Param("min") BigDecimal min,
    					  @Param("max") BigDecimal max,
    					  @Param("catId") String catId);
	
	
    @Query("SELECT i FROM Item i WHERE i.estado = true AND i.id_plato = :id_item")
    Optional<Item> listarUno(@Param("id_item") Integer idItem);
    
    @Query("SELECT i FROM Item i WHERE i.estado = true AND i.text_id = :text_id")
    Optional<Item> listarUno(@Param("text_id") String text_id);
    
    //Optional<Item> findByText_id(@Param("text_id") String textId);
}