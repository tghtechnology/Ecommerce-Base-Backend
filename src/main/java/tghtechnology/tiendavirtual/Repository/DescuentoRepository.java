package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Models.Item;

@Repository
public interface DescuentoRepository extends CrudRepository<Descuento, Integer>{

	@Query("SELECT des FROM Descuento des WHERE des.estado = true")
    List<Descuento> listarDescuento();

    @Query("SELECT des FROM Descuento des WHERE des.estado = true AND des.id_descuento = :des_id")
    Optional<Descuento> listarUno(@Param("des_id") Integer id_descuento);
    
    @Query("SELECT des FROM Descuento des WHERE des.estado = true AND des.activado = true AND des.item = :itm")
    Optional<Descuento> listarPorItem(@Param("itm") Item item);
}
