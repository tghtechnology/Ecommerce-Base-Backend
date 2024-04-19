package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Enums.RegionPeru;
import tghtechnology.tiendavirtual.Models.PuntoShalom;

@Repository
public interface PuntoShalomRepository extends CrudRepository<PuntoShalom, Integer>{
	
	@Query("SELECT ps FROM PuntoShalom ps WHERE ps.estado = true")
    List<PuntoShalom> listar();
	
	@Query("SELECT ps FROM PuntoShalom ps WHERE ps.estado = true AND ps.departamento = :dep")
    List<PuntoShalom> listar(@Param("dep") RegionPeru departamento);

    @Query("SELECT ps FROM PuntoShalom ps WHERE ps.estado = true AND ps.id_punto = :id")
    Optional<PuntoShalom> listarUno(@Param("id") Integer id_publicidad);
}
