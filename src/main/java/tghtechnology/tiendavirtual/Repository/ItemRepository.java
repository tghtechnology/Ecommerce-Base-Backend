package tghtechnology.tiendavirtual.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

	@Query("SELECT i FROM Item i WHERE i.estado = true")
    List<Item> listarItem();

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
    		+ "(i.nombre LIKE %:query% "
    		+ "OR i.descripcion LIKE %:query%) "
    		+ "AND i.precio > :min "
    		+ "AND i.precio < :max "
    		+ "AND (:catId LIKE '' OR c.text_id = :catId)"
    		+ "AND i.estado=true")
    List<Item> listar(@Param("query") String query,
    					  @Param("min") BigDecimal min,
    					  @Param("max") BigDecimal max,
    					  @Param("catId") String catId,
    					  Pageable pageable);
	
	
    @Query("SELECT i FROM Item i WHERE i.estado = true AND i.id_item = :id_item")
    Optional<Item> listarUno(@Param("id_item") Integer idItem);
    
    @Query("SELECT i FROM Item i WHERE i.estado = true AND i.text_id = :text_id")
    Optional<Item> listarUno(@Param("text_id") String text_id);
    
    //Optional<Item> findByText_id(@Param("text_id") String textId);
}
