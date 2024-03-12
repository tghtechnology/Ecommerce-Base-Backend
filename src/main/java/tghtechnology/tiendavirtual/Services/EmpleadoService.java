package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Empleado;
import tghtechnology.tiendavirtual.Repository.EmpleadoRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForList;

@Service
@AllArgsConstructor
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empRepository;
    //private UsuarioRepository userRepository;
    //private UsuarioService userService;


    /*Listar empleado */
    public List<EmpleadoDTOForList> listarEmpleados(){
        List<EmpleadoDTOForList> empDto = new ArrayList<>();
        List<Empleado> emp = (List<Empleado>) empRepository.listarEmpleados();

        emp.forEach(empleado ->{
            empDto.add(new EmpleadoDTOForList().from(empleado));
        });

        return empDto;
    }
    
    /*Obtener un empleado especifico*/
    public EmpleadoDTOForList listarUno( Integer id){
        Empleado empleado = buscarPorId(id);
        return new EmpleadoDTOForList().from(empleado);
    }

    /*Registrar Empleado */
    public EmpleadoDTOForList crearEmpleado(EmpleadoDTOForInsert iEmp){
        //UsuarioDTOForList usuario  = userService.crearUsuario(emp.getUsuario());
        //Usuario user = userRepository.listarUno(usuario.getId_usuario()).get();

        Empleado emp = iEmp.toModel();
        //newEmpleado.setUsuario(user);

        empRepository.save(emp);

        return new EmpleadoDTOForList().from(emp);
    } 
    
    /*Actualizar empleado */
    public void actualizarEmpleado(Integer id, EmpleadoDTOForInsert mEmp){
        Empleado empleado = buscarPorId(id);        
        
        empleado = mEmp.updateModel(empleado);
        
        //userService.actualizarUsuario(empleado.getUsuario().getId_usuario(), us);
        
        empRepository.save(empleado);
    }
    
    /**Eliminar empleado */
    public void eliminarEmpleado(Integer id){
        Empleado emp = buscarPorId(id);
        //userService.eliminarUsuario(emp.getUsuario().getId_usuario());
        emp.setEstado(false);
        empRepository.save(emp);
    }
    
    
    private Empleado buscarPorId(Integer id) {
		return empRepository.obtenerUno(id).orElseThrow( () -> new IdNotFoundException("empleado"));
	}
}
