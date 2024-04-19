package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Publicidad;

@Repository
public interface PublicidadRepository extends CrudRepository<Publicidad, Integer>{

	@Query("SELECT p FROM Publicidad p WHERE p.estado = true")
    List<Publicidad> listar();
	
	@Query("SELECT p FROM Publicidad p WHERE p.estado = true AND p.mostrar = :most")
    List<Publicidad> listar(@Param("most") Boolean mostrar);

    @Query("SELECT p FROM Publicidad p WHERE p.estado = true AND p.id_publicidad = :id")
    Optional<Publicidad> listarUno(@Param("id") Integer id_publicidad);
}