package tghtechnology.chozaazul.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.chozaazul.Models.MenuCumple;
import tghtechnology.chozaazul.Models.Enums.Mes;

public interface MenuRepository extends CrudRepository<MenuCumple, Integer>{

	/**
	 * Listar los activos
	 * @return Lista de menus activos
	 */
	@Query("SELECT mc FROM MenuCumple mc WHERE mc.estado = true")
    List<MenuCumple> listarMenuCumple();
	
    /**
	 * Listar los menus de compleaños según el mes
	 * @param mes El mes del que se desea listar
	 * @return Lista de menus activos que se encuentran en el mes
	 */
    @Query("SELECT mc FROM MenuCumple mc WHERE mc.estado = true AND mc.mes = :mes")
    List<MenuCumple> listarPorMes(@Param("mes") Mes mes);
	
	/**
	 * Listar un menu según su ID
	 * @param id_menu La ID que se desea buscar
	 * @return Opcional que puede contener el Menu si es que se encontró.
	 */
    @Query("SELECT mc FROM MenuCumple mc WHERE mc.estado = true AND mc.id_menu = :id_menu")
    Optional<MenuCumple> listarUno(@Param("id_menu") Integer id_menu);
    
}
