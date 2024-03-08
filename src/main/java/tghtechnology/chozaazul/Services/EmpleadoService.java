package tghtechnology.chozaazul.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Models.Empleado;
import tghtechnology.chozaazul.Models.Usuario;
import tghtechnology.chozaazul.Repository.EmpleadoRepository;
import tghtechnology.chozaazul.Repository.UsuarioRepository;
import tghtechnology.chozaazul.Utils.Exceptions.IdNotFoundException;
import tghtechnology.chozaazul.dto.Empleado.EmpleadoDTOForInsert;
import tghtechnology.chozaazul.dto.Empleado.EmpleadoDTOForList;
import tghtechnology.chozaazul.dto.Empleado.EmpleadoDTOForModify;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForList;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForModify;

@Service
@AllArgsConstructor
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empRepository;
    private UsuarioRepository userRepository;
    private UsuarioService userService;


    /*Listar empleado */
    public List<EmpleadoDTOForList> listarEmpleados(){
        List<EmpleadoDTOForList> empDto = new ArrayList<>();
        List<Empleado> emp = (List<Empleado>) empRepository.listarEmpleados();

        emp.forEach(item ->{
            empDto.add(new EmpleadoDTOForList(item));
        });

        return empDto;
    }
    
    /*Obtener un empleado especifico*/
    public EmpleadoDTOForList listarUno( Integer id){
        Empleado empleado = buscarPorId(id);
        return new EmpleadoDTOForList(empleado);
    }

    /*Registrar Empleado */
    public EmpleadoDTOForList crearEmpleado(EmpleadoDTOForInsert emp){
        UsuarioDTOForList usuario  = userService.crearUsuario(emp.getUsuario());
        Usuario user = userRepository.listarUno(usuario.getId_usuario()).orElseThrow( () -> new IdNotFoundException("usuario"));

        Empleado newEmpleado = new Empleado();
        newEmpleado.setNombres(emp.getNombres());
        newEmpleado.setApellidos(emp.getApellidos());
        newEmpleado.setCorreo(emp.getCorreo());
        newEmpleado.setUsuario(user);
        newEmpleado.setTelefono(emp.getTelefono());
        newEmpleado.setEstado(true);
        newEmpleado.setFecha_creacion(LocalDateTime.now());

        empRepository.save(newEmpleado);

        return new EmpleadoDTOForList(newEmpleado);
    } 
    
    /*Actualizar empleado */
    public void actualizarEmpleado(Integer id, EmpleadoDTOForModify body){
        Empleado empleado = buscarPorId(id);        
        
        UsuarioDTOForModify us = body.getUsuario();
        
        empleado.setNombres(body.getNombres());
        empleado.setApellidos(body.getApellidos());
        empleado.setCorreo(body.getCorreo());
        empleado.setTelefono(body.getTelefono());
        empleado.setEstado(true);
        
        userService.actualizarUsuario(empleado.getUsuario().getId_usuario(), us);
        
        empRepository.save(empleado);
    }
    
    /**Eliminar empleado */
    public void eliminarEmpleado(Integer id){
        Empleado emp = buscarPorId(id);
        
        userService.eliminarUsuario(emp.getUsuario().getId_usuario());
        
        emp.setEstado(false);
        empRepository.save(emp);
        
        
    }
    
    
    private Empleado buscarPorId(Integer id) {
		return empRepository.obtenerUno(id).orElseThrow( () -> new IdNotFoundException("empleado"));
	}
}
