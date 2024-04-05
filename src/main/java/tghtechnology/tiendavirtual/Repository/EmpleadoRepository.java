package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Empleado;



public interface EmpleadoRepository  extends JpaRepository<Empleado, Integer>{
    
    @Query("SELECT e FROM Empleado e WHERE e.estado = true")
    List<Empleado> listarEmpleados(Pageable pageable);

    @Query("SELECT e FROM Empleado e WHERE e.estado = true AND e.id_persona = :id_emp")
    Optional<Empleado> obtenerUno(@Param("id_emp") Integer idEmpleado);
}
