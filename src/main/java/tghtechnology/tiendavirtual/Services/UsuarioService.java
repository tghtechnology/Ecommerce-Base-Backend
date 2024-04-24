package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import lombok.Getter;
import tghtechnology.tiendavirtual.Emails.EmailVerificacion;
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
public class UsuarioService {

    private final UsuarioRepository userRepository;
    private final PersonaRepository perRepository;
    private final PasswordEncoder passwordEncoder;
    
    private final SocketIOAuth socketAuth;
	private final CustomBeanValidator validator;
	private final TokenGenerator tokens;
	private final EmailService emailService;
	private final SettingsService settings;
	private final CustomJwtAuthConverter jwtAuthConverter;
	private final JwtDecoder jwtDecoder;
	
	@Getter
	private final Map<String, LocalDateTime> passChangeRequestCache = new HashMap<>();

    public UsuarioService(UsuarioRepository userRepository,
	    		PersonaRepository perRepository,
				PasswordEncoder passwordEncoder,
				SocketIOAuth socketAuth,
				CustomBeanValidator validator,
				TokenGenerator tokens,
				EmailService emailService,
				SettingsService settings,
				CustomJwtAuthConverter jwtAuthConverter,
				JwtDecoder jwtDecoder) {
		this.userRepository = userRepository;
		this.perRepository = perRepository;
		this.passwordEncoder = passwordEncoder;
		this.socketAuth = socketAuth;
		this.validator = validator;
		this.tokens = tokens;
		this.emailService = emailService;
		this.settings = settings;
		this.jwtAuthConverter = jwtAuthConverter;
		this.jwtDecoder = jwtDecoder;
	}

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
    
    
    /**
     * Solicita una verificación de cuenta para un usuario.
     * @param auth La autenticación del usuario
     * @throws MessagingException Si hay un error al enviar el correo de verificación.
     * @throws DataMismatchException Si el usuario ya está verificado
     */
    public void solicitar_verificacion(CustomJwtAuthToken auth) throws MessagingException {
    	
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
    
    /**
     * Intenta verificar al usuario según un token de verificación recibido desde el email 
     * enviado por {@link #solicitar_verificacion(CustomJwtAuthToken) solicitar_verificacion}.
     * @param oldAuth La autenticación del usuario (si es que está verificándose desde el mismo navegador)
     * @param token El token de verificación.
     * @return Una respuesta de login que incluye un nuevo token de verificación si es que la autenticación
     * no es {@code null} y si coincide con el usuario que solicitó la verificación. En otro caso devuelve 
     * {@code null}
     * @throws AccountConfigurationException Si:<br>
     * - Hubo un error al leer el token de verificación. <br>
     * - El token proporcionado no es de verificación. <br>
     * - El usuario ya está validado.
     */
    public UsuarioDTOForLoginResponse verificar_usuario(CustomJwtAuthToken oldAuth, String token) {
    	
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
    
    /**
     * Solicita un cambio de contraseña para el usuario correspondiente al
     * nombre de usuario proporcionado y guarda un Cache de las solicitudes. <br>
     * Si el usuario no existe, no devuelve un error, pero si guarda el registro.
     * @param username El nombre de usuario de la cuenta a la cual solicitar cambio de contraseña.
     * @throws MessagingException Si hay un error al enviar el correo de cambio
     */
    public void solicitar_cambio_pass(String username) throws MessagingException {
    	
    	final Integer interval = settings.getInt("seguridad.chpass_interval");
    	final LocalDateTime now = LocalDateTime.now();
    	final LocalDateTime nextRequest = passChangeRequestCache.get(username);
    	
    	// Limitar las solicitudes de cambio de contraseña para que
    	// solo se realizen cada X minutos (definido en Settings).
    	if(nextRequest != null) {
    		if(nextRequest.isBefore(now))
    			throw new DataMismatchException("usuario", String.format("Ya se solicitó un cambio de contraseña para esta cuenta. Espera %d minutos", interval));
    	}
    	
    	Usuario user = userRepository.listarPorUserName(username).orElse(null);
    	
    	// Guardar el intento en el cache local
    	passChangeRequestCache.put(username, now.plus(interval, ChronoUnit.MINUTES));
    	
    	// Si el usuario no existe, devolver respuesta correcta.
    	// No notificar al usuario si cierta cuenta existe o no.
    	if(user == null) return;

    	// Enviar el correo de cambio de contraseña junto con el token respectivo
    	String token = tokens.changePassToken(user);
    	
//    	try {
//    	emailService.enviarEmail(new EmailVerificacion(user, 
//    									settings.getString("url.seguridad.changepass"),
//    									token));
//    	} catch( MessagingException ex) {
//    		// No guardar en cache si hubo un error y no se envio el correo
//    		passChangeRequestCache.remove(username);
//    		throw ex;
//    	}
    	System.out.println(token);
    }
    
    public void cambiar_pass(String token, String newPass) {
    	
    	CustomJwtAuthToken auth;
    	try {
    		auth = (CustomJwtAuthToken) jwtAuthConverter.convert(jwtDecoder.decode(token));
    	} catch (Exception ex) {
    		throw new AccountConfigurationException("Error al leer el token");
    	}
    	
    	if(auth.getAction() != TokenActions.CHANGE_PASSWORD)
    		throw new AccountConfigurationException("Token incorrecto");
    	
    	Usuario user = buscarPorUserName(auth.getName());
    	user.setHashed_pass(passwordEncoder.encode(newPass));
    	
    	userRepository.save(user);
    }
    
    private Usuario buscarPorId(Integer id) {
		return userRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("usuario"));
	}
    
    private Usuario buscarPorUserName(String username) {
		return userRepository.listarPorUserName(username).orElseThrow( () -> new IdNotFoundException("usuario"));
	}

}
