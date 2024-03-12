package tghtechnology.tiendavirtual.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tghtechnology.tiendavirtual.Enums.TipoImagen;
import tghtechnology.tiendavirtual.Models.Imagen;

@Repository
public interface ImagenRepository extends CrudRepository<Imagen, Integer> {
	
	// Listar por un objeto
    @Query("SELECT i FROM Imagen i WHERE i.tipo=:tipo AND i.id_owner=:id_owner")
    List<Imagen> listarPorObjeto(@Param("tipo")TipoImagen tipo,
    							 @Param("id_owner") Integer id_owner);
    
    // Eliminar por objeto
    @Transactional
    @Modifying
    @Query("DELETE FROM Imagen i WHERE i.tipo=:tipo AND i.id_owner=:id_owner")
    List<Imagen> eliminarPorObjeto(@Param("tipo")TipoImagen tipo,
								   @Param("id_owner") Integer id_owner);
}
