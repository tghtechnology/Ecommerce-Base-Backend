package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Rebaja;

@Repository
public interface RebajaRepository extends CrudRepository<Rebaja, Integer>{

	@Query("SELECT r FROM Rebaja r WHERE r.estado = true "
			+ "AND r.es_evento = false "
			+ "AND (:ext_perm = true OR r.activo = true)")
    List<Rebaja> listarRebajas(@Param("ext_perm") Boolean ext_perm);
    
	@Query("SELECT r FROM Rebaja r WHERE r.estado = true "
			+ "AND r.es_evento = true")
    List<Rebaja> listarEventos();
	
	@Query("SELECT r FROM Rebaja r WHERE r.estado = true "
			+ "AND r.activo = true "
			+ "AND r.es_evento = true")
    Optional<Rebaja> listarEvento();
	
    @Query("SELECT r FROM Rebaja r WHERE r.estado = true AND r.id_rebaja = :id")
    Optional<Rebaja> listarUno(@Param("id") Integer id_rebaja);
}
