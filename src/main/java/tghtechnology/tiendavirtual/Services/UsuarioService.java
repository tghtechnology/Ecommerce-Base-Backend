package tghtechnology.tiendavirtual.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Enums.TipoCargo;
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
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForModify;

@Service
@AllArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	private CustomBeanValidator validator;


    /*Listar usuarios */
    public List<UsuarioDTOForList> listarUsuarios(){
        List<UsuarioDTOForList> userList = new ArrayList<>();
        List<Usuario> users = userRepository.listUser();

        users.forEach(item -> {
            userList.add(new UsuarioDTOForList(item));
        });
        return userList;
    }

    /*Crear usuario */
    public UsuarioDTOForList crearUsuario(UsuarioDTOForInsert user){
        Usuario usuario = new Usuario();
        usuario.setUsername(user.getUsername());
        
        usuario.setCargo(user.getCargo());
        //Usuario.setCargo(user.getCargo());
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setEstado(true);
        
        
        //encriptar pass
        validarPass(user.getPassword());
        
		usuario.setPassword(passwordEncoder.encode(user.getPassword().getPassword()));
        
        userRepository.save(usuario);
        return new UsuarioDTOForList(usuario);
    }
    
  //Crear por defecto
  	public UsuarioDTOForList crearUsuarioDefault(String StringUs) throws JsonMappingException, JsonProcessingException, CustomValidationFailedException {
  		
  		if(!userRepository.listUser().isEmpty()) {
  			throw new BadCredentialsException(null);
  		}
  		
  		UsuarioDTOForFirstLogin iUs = new ObjectMapper().readValue(StringUs, UsuarioDTOForFirstLogin.class);
		validator.validar(iUs);
  		
  		Usuario us = new Usuario();
  		us.setUsername(iUs.getUsername());
  		us.setCargo(TipoCargo.GERENTE);
  		us.setFechaCreacion(LocalDateTime.now());
  		us.setEstado(true);
  		
  		//encriptar pass
  		validarPass(iUs.getPassword());
  		us.setPassword(passwordEncoder.encode(iUs.getPassword().getPassword()));
  		
  		us = userRepository.save(us);
  		
  		return new UsuarioDTOForList(us);
  	}

    /*Actualizar usuario*/
    public void actualizarUsuario(Integer id, UsuarioDTOForModify body){
        Usuario usuario = buscarPorId(id);
        usuario.setUsername(body.getUsername());
        usuario.setCargo(body.getCargo());
        
        if(body.getPassword() != null) {
        	validarPass(body.getPassword());
      		usuario.setPassword(passwordEncoder.encode(body.getPassword().getPassword()));
        }
        
        userRepository.save(usuario);
    }
    
    /*Eliminar usuario */
    public void eliminarUsuario(Integer id){
        Usuario user = userRepository.listarUno(id).orElseThrow(()-> new IdNotFoundException("usuario"));
        user.setEstado(false);
        userRepository.save(user);
    }
    
    /**
     * GEnerar respuesta de login
     */
    public UsuarioDTOForLoginResponse devolverLogin(String username, String token) {
    	Usuario user = userRepository.listarPorUserName(username).get();
    	
    	UsuarioDTOForLoginResponse response = new UsuarioDTOForLoginResponse();
    	response.setId(user.getId_usuario());
    	response.setUsername(username);
    	response.setCargo(user.getCargo());
    	response.setToken(token);
    	
    	return response;
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
