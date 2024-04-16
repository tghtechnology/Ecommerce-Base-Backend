package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Valoracion;

public interface ValoracionRepository extends JpaRepository<Valoracion, Integer>{

	@Query("SELECT v FROM Valoracion v "
			+ "WHERE v.estado = true "
			+ "AND v.item = :itm "
			+ "AND (:stars = -1 OR v.estrellas = :stars)"
			)
    List<Valoracion> listarPorItem(@Param("itm") Item item, @Param("stars") Short stars, Pageable page);
	
	@Query("SELECT v FROM Valoracion v WHERE v.estado = true AND v.usuario = :user")
    List<Valoracion> listarPorUsuario(@Param("user") Usuario usuario, Pageable page);
	
	@Query("SELECT v FROM Valoracion v WHERE v.estado = true "
			+ "AND v.item = :item "
			+ "AND v.usuario = :user")
    Optional<Valoracion> listarUno(@Param("item") Item item, @Param("user") Usuario user);
	
    @Query("SELECT v FROM Valoracion v WHERE v.estado = true AND v.id_valoracion = :id_val")
    Optional<Valoracion> listarUno(@Param("id_val") Integer id_valoracion);
}
