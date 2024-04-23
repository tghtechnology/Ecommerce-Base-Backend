package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Enums.TokenActions;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Repository.PersonaRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Security.CustomJwtAuthConverter;
import tghtechnology.tiendavirtual.Security.CustomJwtAuthToken;
import tghtechnology.tiendavirtual.Security.TokenDetails;
import tghtechnology.tiendavirtual.Security.TokenGenerator;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Emails.EmailService;
import tghtechnology.tiendavirtual.Utils.Exceptions.AccountConfigurationException;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.Utils.Sockets.SocketIOAuth;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForFirstLogin;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForInsert;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForList;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForLoginResponse;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForModify;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository userRepository;
    private PersonaRepository perRepository;
    private PasswordEncoder passwordEncoder;
    
    private SocketIOAuth socketAuth;
	private CustomBeanValidator validator;
	private TokenGenerator tokens;
	private EmailService emailService;
	private SettingsService settings;
	CustomJwtAuthConverter jwtAuthConverter;
	private JwtDecoder jwtDecoder;

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
    		if(auth.getName().equals(user.getUsername()) || TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE))
    			return new UsuarioDTOForList().from(user);
    		else
    			throw new AccessDeniedException(null);
    	} else
    		throw new AccessDeniedException(null);
    }

    /*Crear usuario */
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Usuario crearUsuario(UsuarioDTOForInsert user){
    	
    	if(user.getPersona() == null)
    		throw new DataMismatchException("persona", "No se ha proporcionado una persona");
    	
    	Persona per = perRepository.save(user.getPersona().toModel());
    	
    	return crearUsuarioPorInstancia(user, per);
    }
    
    /*Crear usuario por id (solo interno) */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public Usuario crearUsuarioPorID(UsuarioDTOForInsert user){
    	
    	if(user.getId_persona() == null)
    		throw new DataMismatchException("persona", "No se ha proporcionado una persona");
    	
    	Persona per = perRepository.obtenerUno(user.getId_persona())
    					.orElseThrow(() -> new IdNotFoundException("persona"));
    	
        return crearUsuarioPorInstancia(user, per);
    }
    
    /*Crear usuario por instancia (solo interno) */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public Usuario crearUsuarioPorInstancia(UsuarioDTOForInsert user, Persona per){
    	
        Usuario usuario = user.toModel();
        usuario.setPersona(per);
        
		usuario.setHashed_pass(passwordEncoder.encode(user.getPassword()));
        
        usuario = userRepository.save(usuario);
        return usuario;
    }
    
    //Crear por defecto
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
  	public UsuarioDTOForList crearAdminDefault(String StringUs) throws CustomValidationFailedException, IOException {
  		
  		if(!userRepository.listUser().isEmpty()) {
  			throw new BadCredentialsException(null);
  		}
  		
  		
  		UsuarioDTOForFirstLogin iUs = new ObjectMapper().readValue(StringUs, UsuarioDTOForFirstLogin.class);
		validator.validar(iUs);
  		
  		Usuario us = iUs.toModel();
  		
  		//encriptar pass
  		us.setHashed_pass(passwordEncoder.encode(iUs.getPassword()));
  		
  		Persona per = perRepository.save(us.getPersona());
  		us.setPersona(per);
  		
  		us = userRepository.save(us);
  		
  		return new UsuarioDTOForList().from(us);
  	}

    /*Actualizar usuario*/
    public void actualizarUsuarioCliente(Usuario usuario, UsuarioDTOForModify body, Authentication auth){
        
        //Comparar que la pass sea correcta
        if(passwordEncoder.matches(body.getOld_password(), usuario.getHashed_pass())) {
        	usuario = body.updateModel(usuario);
        	if(body.getNew_password() != null) {
          		usuario.setHashed_pass(passwordEncoder.encode(body.getNew_password()));
            }
        	userRepository.save(usuario);
        } else {
        	throw new DataMismatchException("password", "La contraseña no es correcta");
        }
    }
    
    /*Actualizar usuario*/
    public void actualizarUsuarioAdmin(Integer id, UsuarioDTOForInsert body, Authentication auth){
        Usuario usuario = buscarPorId(id);

        if(!auth.getName().equals(body.getEmail()))
        	throw new AccessDeniedException("Usuario no corresponde");
        
        usuario = body.updateModel(usuario);
        
        if(body.getPassword() != null) {
      		usuario.setHashed_pass(passwordEncoder.encode(body.getPassword()));
        }
        
        userRepository.save(usuario);
    }
    
    /*Eliminar usuario */
    public void eliminarUsuario(Integer id){
        Usuario user = buscarPorId(id);
        user.setUsername("DELETED#" + user.getId_usuario());
        user.setEstado(false);
        userRepository.save(user);
    }
    
    /**
     * Generar respuesta de login
     */
    public UsuarioDTOForLoginResponse devolverLogin(String username, String token) {
    	Usuario user = userRepository.listarPorUserName(username).get();
    	
    	UUID uid = null;
    	if(TipoUsuario.checkRole(user.getAuthorities(), TipoUsuario.GERENTE))
    		uid = socketAuth.add();
    	
    	return new UsuarioDTOForLoginResponse().from(user, token, uid);
    }
    
    /* Solicitar validacion */
    public void solicitar_validacion(CustomJwtAuthToken auth) throws MessagingException {
    	
    	TokenDetails dets = TokenDetails.getDetails(auth);
    	
    	
    	// Finalizar si el usuario ya esta validado
    	if(dets.getVerificado())
    		throw new DataMismatchException("usuario", "El usuario ya esta verificado");
    	
    	// TODO comprobar intervalo de validacion
    	Usuario user = buscarPorUserName(auth.getName());
    	String token = tokens.verificationToken(user);
//    	emailService.enviarEmail(new EmailVerificacion(user, 
//    									settings.getString("url.seguridad.validacion"),
//    									token));
    	System.out.println(token);
    	System.out.println(dets);
    }
    
    /* Verificar usuario */
    public UsuarioDTOForLoginResponse verificar_usuario(CustomJwtAuthToken oldAuth, String token) throws MessagingException {
    	
    	CustomJwtAuthToken auth;
    	try {
    		auth = (CustomJwtAuthToken) jwtAuthConverter.convert(jwtDecoder.decode(token));
    	} catch (Exception ex) {
    		throw new AccountConfigurationException("Error al leer el token");
    	}
    	
    	if(auth.getAction() != TokenActions.VERIFY)
    		throw new AccountConfigurationException("Token incorrecto");
    	
    	Usuario user = buscarPorUserName(auth.getName());
    	// Si el usuario ya esta validado
    	if(user.isAutenticado())
    		throw new AccountConfigurationException("El usuario ya esta validado");
    	
    	user.setAutenticado(true);
    	userRepository.save(user);
    	
    	// Si el token activo se corresponde al usuario, devolver un nuevo token con el campo de verificado activado
    	if(oldAuth != null && oldAuth.getName().equals(auth.getName())) {
    		String newToken = tokens.loginToken(auth, true);
    		return devolverLogin(user.getUsername(),newToken); 
    	}
    	
    	return null;
    }
    
    private Usuario buscarPorId(Integer id) {
		return userRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("usuario"));
	}
    
    private Usuario buscarPorUserName(String username) {
		return userRepository.listarPorUserName(username).orElseThrow( () -> new IdNotFoundException("usuario"));
	}

}
