package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer>{

	@Query("SELECT m FROM Marca m WHERE m.estado = true")
    List<Marca> listar(Pageable pageable);

    @Query("SELECT m FROM Marca m WHERE m.estado = true")
    List<Marca> listarFull();
    
    @Query("SELECT m FROM Marca m LEFT JOIN m.categorias c WHERE m.estado = true AND c.id_categoria = :cat_id")
    List<Marca> listarPorCategoria(@Param("cat_id") Integer id_categoria);

    @Query("SELECT m FROM Marca m WHERE m.estado = true AND m.id_marca = :mar_id")
    Optional<Marca> listarUno(@Param("mar_id") Integer id_marca);
    
    @Query("SELECT m FROM Marca m WHERE m.estado = true AND m.text_id = :text_id")
    Optional<Marca> listarUno(@Param("text_id") String text_id);
}
