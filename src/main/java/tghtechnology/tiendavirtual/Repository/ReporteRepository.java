package tghtechnology.tiendavirtual.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.ReporteMensual;
public interface ReporteRepository extends CrudRepository<ReporteMensual, Integer>{

	@Query("SELECT r FROM ReporteMensual r WHERE r.id.anio = :anio")
    List<ReporteMensual> listarPorAnio(@Param("anio") Integer anio);
	
}
