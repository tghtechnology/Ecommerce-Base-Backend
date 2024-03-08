package tghtechnology.tiendavirtual.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import tghtechnology.tiendavirtual.Models.Promocion;
import tghtechnology.tiendavirtual.Models.Composite.PromocionPlato;

@Repository
public interface PromocionPlatoRepository extends CrudRepository<PromocionPlato, Integer>{

	@Query("SELECT prpl FROM PromocionPlato prpl WHERE prpl.promocion=:prom")
    List<Promocion> listaPorPromocion(@Param("prom") Promocion promocion);

	@Modifying
	@Transactional
	@Query("DELETE FROM PromocionPlato prpl WHERE prpl.promocion=:prom")
    void eliminarPorPromocion(@Param("prom") Promocion promocion);
    
    
    
}
