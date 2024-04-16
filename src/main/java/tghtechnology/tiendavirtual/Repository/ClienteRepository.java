package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Cliente;



public interface ClienteRepository  extends JpaRepository<Cliente, Integer>{
    
	@Query("SELECT c FROM Cliente c WHERE c.estado = true")
    List<Cliente> listarClientes(Pageable pageable);
	
	@Query("SELECT c FROM Cliente c WHERE c.estado = true AND c.id_persona = :cli_id")
    Optional<Cliente> listarUno(@Param("cli_id") Integer cli_id);
	
}
