package tghtechnology.tiendavirtual.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Empleado;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Repository.EmpleadoRepository;
import tghtechnology.tiendavirtual.Repository.PersonaRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForList;

@Service
@AllArgsConstructor
public class EmpleadoService {
    
    private EmpleadoRepository empRepository;
    private PersonaRepository perRepository;
    private UsuarioRepository userRepository;
    
    private UsuarioService userService;
    private SettingsService settings;

    /**
     * Lista todos los empleados activos.
     * @return Una lista de los empleados en formato DTOForList.
     */
    public List<EmpleadoDTOForList> listarEmpleados(Integer pagina){
        List<EmpleadoDTOForList> empDto = new ArrayList<>();
        
        Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.empleado"));
        List<Empleado> emp = (List<Empleado>) empRepository.listarEmpleados(pag);

        emp.forEach(empleado ->{
            empDto.add(new EmpleadoDTOForList().from(empleado));
        });

        return empDto;
    }
    
    /**
     * Obtiene un empleado en específico.
     * @param id La ID del empleado a buscar.
     * @return El empleado en formato DTOForList
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    public EmpleadoDTOForList listarUno( Integer id){
        Empleado empleado = buscarPorId(id);
        return new EmpleadoDTOForList().from(empleado);
    }

    /**
     * Registra un nuevo empleado.
     * Se puede proporcionar la persona mediante una id o datos en formato DTOForInsert,
     * pero ambos no pueden ser nulos al mismo tiempo.
     * @param iEmp El empleado a registrar en formato DTOForInsert.
     * @param auth La instancia de autenticación del empleado que realiza la operación
     * @return El empleado creado en formato DTOForList.
     * @throws DataMismatchException Si ambos campos de identificación de persona son nulos.
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public EmpleadoDTOForList crearEmpleado(EmpleadoDTOForInsert iEmp, Authentication auth){
    	
    	if(!TipoUsuario.checkRole(auth.getAuthorities(), iEmp.getUsuario().getCargo()))
    		throw new AccessDeniedException("");
    	
    	// Asignar el mismo correo al usuario y persona
    	iEmp.getPersona().setCorreo_personal(iEmp.getUsuario().getEmail());
    	
    	Persona per = perRepository.save(iEmp.getPersona().toModel());
    	
    	Empleado emp = iEmp.toModel();
    	emp.setPersona(per);
    	emp.setId_persona(per.getId_persona());
    	
    	iEmp.getUsuario().setId_persona(per.getId_persona());
    	Usuario user = userService.crearUsuarioPorInstancia(iEmp.getUsuario(), per);
        
        emp.setUsuario(user);
        emp = empRepository.save(emp);

        return new EmpleadoDTOForList().from(emp);
    } 
    
    /**
     * Actualiza un empleado.
     * @param id ID del empleado a actualizar.
     * @param mEmp Datos del empleado en formato DTOForInsert. El campo persona no puede ser nulo.
     * @param auth La instancia de autenticación del empleado que realiza la operación
     * @throws DataMismatchException Si el campo persona es nulo.
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, AccessDeniedException.class})
    public void actualizarEmpleado(Integer id, EmpleadoDTOForInsert mEmp, Authentication auth){
    	
    	if(mEmp.getPersona() == null)
    		throw new DataMismatchException("persona", "el campo no puede ser nulo");
    	
        Empleado empleado = buscarPorId(id);        
        
        // No permite modificar un usuario con rol superior
        // o modificar un usuario para que tenga un rol superior
        if(!TipoUsuario.checkRole(auth.getAuthorities(), mEmp.getUsuario().getCargo()) ||
           !TipoUsuario.checkRole(auth.getAuthorities(), empleado.getUsuario().getCargo()))
    		throw new AccessDeniedException("");
        
        if(mEmp.getUsuario().getCargo().equals(TipoUsuario.CLIENTE))
        	throw new DataMismatchException("cargo", "No se puede cambiar el cargo a cliente");
        
        empleado = mEmp.updateModel(empleado);
        
        perRepository.save(empleado.getPersona());
        userRepository.save(empleado.getUsuario());
        empRepository.save(empleado);
    }
    
    /**
     * Elimina un empleado.
     * @param id La ID del empleado a eliminar.
     * @param auth La instancia de autenticación del empleado que realiza la operación
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    public void eliminarEmpleado(Integer id, Authentication auth){
        Empleado emp = buscarPorId(id);
        
        // No permite eliminar un usuario con rol superior
        if(!TipoUsuario.checkRole(auth.getAuthorities(), emp.getUsuario().getCargo()))
    		throw new AccessDeniedException("");
        
        emp.setFecha_modificacion(LocalDateTime.now());
        emp.setEstado(false);
        emp = empRepository.save(emp);
        
        Usuario user = emp.getUsuario();
        user.setEstado(false);
        userRepository.save(user);
        
    }
    
    
    private Empleado buscarPorId(Integer id) {
		return empRepository.obtenerUno(id).orElseThrow( () -> new IdNotFoundException("empleado"));
	}
}
