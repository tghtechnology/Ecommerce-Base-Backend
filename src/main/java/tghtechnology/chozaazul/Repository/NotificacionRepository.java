package tghtechnology.chozaazul.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tghtechnology.chozaazul.Models.Notificacion;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Integer> {
	
	// Listar los activos
    @Query("SELECT n FROM Notificacion n WHERE n.activo=true")
    List<Notificacion> listar();
    
    // Listar los no leidos
    @Query("SELECT n FROM Notificacion n WHERE n.activo=true AND n.visto=false")
    List<Notificacion> listarNoLeidos();
}
