package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Empleado;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Repository.EmpleadoRepository;
import tghtechnology.tiendavirtual.Repository.PersonaRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Empleado.EmpleadoDTOForList;

@Service
@AllArgsConstructor
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empRepository;
    private PersonaRepository perRepository;


    /**
     * Lista todos los empleados activos.
     * @return Una lista de los empleados en formato DTOForList.
     */
    public List<EmpleadoDTOForList> listarEmpleados(){
        List<EmpleadoDTOForList> empDto = new ArrayList<>();
        List<Empleado> emp = (List<Empleado>) empRepository.listarEmpleados();

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
     * @return El empleado creado en formato DTOForList.
     * @throws DataMismatchException Si ambos campos de identificación de persona son nulos.
     */
    public EmpleadoDTOForList crearEmpleado(EmpleadoDTOForInsert iEmp){

    	if(iEmp.getId_persona() == null && iEmp.getPersona() == null)
    		throw new DataMismatchException("persona", "No se ha proporcionado una persona");
    	
    	Persona per = perRepository.obtenerUno(iEmp.getId_persona())
    					.orElseGet(() -> perRepository.save(iEmp.getPersona().toModel()));
        
    	Empleado emp = iEmp.toModel();
    	emp.setPersona(per);
    	
        emp = empRepository.save(emp);

        return new EmpleadoDTOForList().from(emp);
    } 
    
    /**
     * Actualiza un empleado.
     * @param id ID del empleado a actualizar.
     * @param mEmp Datos del empleado en formato DTOForInsert. El campo persona no puede ser nulo.
     * @throws DataMismatchException Si el campo persona es nulo.
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    public void actualizarEmpleado(Integer id, EmpleadoDTOForInsert mEmp){
    	
    	if(mEmp.getPersona() == null)
    		throw new DataMismatchException("persona", "el campo no puede ser nulo");
    	
        Empleado empleado = buscarPorId(id);        
        
        empleado = mEmp.updateModel(empleado);
        
        perRepository.save(empleado.getPersona());
        empRepository.save(empleado);
    }
    
    /**
     * Elimina un empleado.
     * @param id La ID del empleado a eliminar.
     * @throws IdNotFoundException Si la ID no se corresponde con ningun empleado.
     */
    public void eliminarEmpleado(Integer id){
        Empleado emp = buscarPorId(id);
        emp.setEstado(false);
        //TODO: reducir rol de usuario a cliente
        empRepository.save(emp);
    }
    
    
    private Empleado buscarPorId(Integer id) {
		return empRepository.obtenerUno(id).orElseThrow( () -> new IdNotFoundException("empleado"));
	}
}
