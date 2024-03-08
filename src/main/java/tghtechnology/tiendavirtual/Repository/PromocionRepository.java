package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Promocion;

@Repository
public interface PromocionRepository extends CrudRepository<Promocion, Integer>{

	@Query("SELECT p FROM Promocion p WHERE p.estado = true")
    List<Promocion> listarPromociones();

    @Query("SELECT p FROM Promocion p WHERE p.estado = true AND p.id_promocion = :id")
    Optional<Promocion> listarUno(@Param("id") Integer id_promocion);
    
    
    
}
