package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Especificacion;
import tghtechnology.tiendavirtual.Models.Variacion;

public interface EspecificacionRepository extends JpaRepository<Especificacion, Integer>{

	@Query("SELECT e FROM Especificacion e WHERE e.estado = true AND e.variacion = :var")
    List<Especificacion> listarPorVariacion(@Param("var") Variacion variacion);
	
    @Query("SELECT e FROM Especificacion e WHERE e.estado = true AND e.id_especificacion = :id_esp")
    Optional<Especificacion> listarUno(@Param("id_esp") Integer id_especificacion);
}
