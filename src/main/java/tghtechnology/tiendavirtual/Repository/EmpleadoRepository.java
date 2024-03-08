package tghtechnology.tiendavirtual.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Empleado;

import java.util.List;
import java.util.Optional;



public interface EmpleadoRepository  extends CrudRepository<Empleado, Integer>{
    
    @Query("SELECT e FROM Empleado e WHERE e.estado = true")
    List<Empleado> listarEmpleados();

    @Query("SELECT e FROM Empleado e WHERE e.estado = true AND e.id_empleado = :id_emp")
    Optional<Empleado> obtenerUno(@Param("id_emp") Integer idEmpleado);
}
