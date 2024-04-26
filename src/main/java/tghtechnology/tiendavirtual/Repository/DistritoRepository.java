package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Enums.DistritoLima;
import tghtechnology.tiendavirtual.Models.DistritoDelivery;

@Repository
public interface DistritoRepository extends CrudRepository<DistritoDelivery, Integer>{

	@Query("SELECT dd FROM DistritoDelivery dd")
    List<DistritoDelivery> listar();
	
	@Query("SELECT dd FROM DistritoDelivery dd WHERE dd.activo =:act")
    List<DistritoDelivery> listarPorActivo(@Param("act") Boolean activo);

    @Query("SELECT dd FROM DistritoDelivery dd WHERE dd.id_distrito = :dist")
    Optional<DistritoDelivery> listarUno(@Param("dist") DistritoLima dist);
    
}
