package tghtechnology.tiendavirtual.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tghtechnology.tiendavirtual.Models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
    

    @Query("SELECT u FROM Usuario u WHERE u.estado = true")
    List<Usuario> listUser();

    @Query("SELECT u FROM Usuario u WHERE u.estado = true AND u.id_persona = :id_user")
    Optional<Usuario> listarUno(@Param("id_user") Integer idUser);

 // Obtener un usuario por su nombre de usuario
 	@Query("SELECT u FROM Usuario u WHERE u.estado=true AND u.username=:uname")
 	Optional<Usuario> listarPorUserName(@Param("uname") String username);
    
    
}
