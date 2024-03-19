package tghtechnology.tiendavirtual.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Persona;



public interface PersonaRepository  extends CrudRepository<Persona, Integer>{

    @Query("SELECT e FROM Empleado e WHERE e.estado = true AND e.id_persona = :id_persona")
    Optional<Persona> obtenerUno(@Param("id_persona") Integer id_persona);
}
