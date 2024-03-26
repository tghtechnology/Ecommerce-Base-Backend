package tghtechnology.tiendavirtual.Repository;

import org.springframework.data.repository.CrudRepository;

import tghtechnology.tiendavirtual.Models.DetalleCarrito;


public interface DetalleVentaRepository extends CrudRepository<DetalleCarrito, Integer>{
    
}