package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Integer>{

	@Query("SELECT ct FROM Categoria ct WHERE ct.estado = true")
    List<Categoria> listarCategoria();

    @Query("SELECT ct FROM Categoria ct WHERE ct.estado = true AND ct.id_categoria = :cat_id")
    Optional<Categoria> listarUno(@Param("cat_id") Integer idCategoria);
    
    @Query("SELECT ct FROM Categoria ct WHERE ct.estado = true AND ct.text_id = :text_id")
    Optional<Categoria> listarUno(@Param("text_id") String text_id);
}
