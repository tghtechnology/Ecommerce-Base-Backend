package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Repository.PersonaRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Usuario.PasswordDTO;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForFirstLogin;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForInsert;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForList;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForLoginResponse;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository userRepository;
    private PersonaRepository perRepository;
    private PasswordEncoder passwordEncoder;
    
	private CustomBeanValidator validator;


    /*Listar usuarios */
    public List<UsuarioDTOForList> listarUsuarios(){
        List<UsuarioDTOForList> userList = new ArrayList<>();
        List<Usuario> users = userRepository.listUser();

        users.forEach(x -> {
            userList.add(new UsuarioDTOForList().from(x));
        });
        return userList;
    }
    
    public UsuarioDTOForList listarUsuario(Integer id, Authentication auth) {
    	
    	Optional<Usuario> oUser = userRepository.listarUno(id);
    	if(oUser.isPresent()) {
    		Usuario user = oUser.get();
    		if(auth.getName().equals(user.getUsername()) || TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.CLIENTE))
    			return new UsuarioDTOForList().from(user);
    		else
    			throw new AccessDeniedException(null);
    	} else
    		throw new AccessDeniedException(null);
    }

    /*Crear usuario */
    public Usuario crearUsuario(UsuarioDTOForInsert user){
    	
    	if(user.getPersona() == null)
    		throw new DataMismatchException("persona", "No se ha proporcionado una persona");
    	
    	Persona per = perRepository.save(user.getPersona().toModel());
    	
    	System.out.println(per.getId_persona());
    	
        Usuario usuario = user.toModel();
        usuario.setPersona(per);
        usuario.setId_persona(per.getId_persona());
        
        //encriptar pass
        validarPass(user.getPassword());
		usuario.setHashed_pass(passwordEncoder.encode(user.getPassword().getPassword()));
		
        usuario = userRepository.save(usuario);
        return usuario;
    }
    
    /*Crear usuario por id (solo interno) */
    public Usuario crearUsuarioPorID(UsuarioDTOForInsert user){
    	
    	if(user.getId_persona() == null && user.getPersona() == null)
    		throw new DataMismatchException("persona", "No se ha proporcionado una persona");
    	
    	Persona per = perRepository.obtenerUno(user.getId_persona())
    					.orElseGet(() -> perRepository.save(user.getPersona().toModel()));
    	
        Usuario usuario = user.toModel();
        usuario.setPersona(per);
        
        //encriptar pass
        validarPass(user.getPassword());
        
		usuario.setHashed_pass(passwordEncoder.encode(user.getPassword().getPassword()));
        
        usuario = userRepository.save(usuario);
        return usuario;
    }
    
    //Crear por defecto
  	public UsuarioDTOForList crearAdminDefault(String StringUs) throws JsonMappingException, JsonProcessingException, CustomValidationFailedException {
  		
  		if(!userRepository.listUser().isEmpty()) {
  			throw new BadCredentialsException(null);
  		}
  		
  		UsuarioDTOForFirstLogin iUs = new ObjectMapper().readValue(StringUs, UsuarioDTOForFirstLogin.class);
		validator.validar(iUs);
  		
  		Usuario us = iUs.toModel();
  		
  		//encriptar pass
  		validarPass(iUs.getPassword());
  		us.setHashed_pass(passwordEncoder.encode(iUs.getPassword().getPassword()));
  		
  		us = userRepository.save(us);
  		
  		return new UsuarioDTOForList().from(us);
  	}

    /*Actualizar usuario*/
    public void actualizarUsuario(Integer id, UsuarioDTOForInsert body, UserDetails userDetails){
        Usuario usuario = buscarPorId(id);

        if(!userDetails.getUsername().equals(body.getEmail()))
        	throw new AccessDeniedException("Usuario no corresponde");
        
        usuario = body.updateModel(usuario);
        
        if(body.getPassword() != null) {
        	validarPass(body.getPassword());
      		usuario.setHashed_pass(passwordEncoder.encode(body.getPassword().getPassword()));
        }
        
        userRepository.save(usuario);
    }
    
    /*Eliminar usuario */
    public void eliminarUsuario(Integer id){
        Usuario user = buscarPorId(id);
        user.setEstado(false);
        userRepository.save(user);
    }
    
    /**
     * Generar respuesta de login
     */
    public UsuarioDTOForLoginResponse devolverLogin(String username, String token) {
    	Usuario user = userRepository.listarPorUserName(username).get();
    	return new UsuarioDTOForLoginResponse().from(user, token);
    }
    
    private Usuario buscarPorId(Integer id) {
		return userRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("usuario"));
	}
    
    private void validarPass(PasswordDTO pass) {
  		if(pass.getPassword() == null
	  		   || pass.getPassword2() == null
	  		   || !pass.getPassword().equals(pass.getPassword2())) {
  			throw new DataMismatchException("password", "Las contraseñas no concuerdan");
  		}
    }
}
