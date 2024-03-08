package tghtechnology.chozaazul.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Models.MenuCumple;
import tghtechnology.chozaazul.Models.Plato;
import tghtechnology.chozaazul.Models.Enums.Mes;
import tghtechnology.chozaazul.Repository.MenuRepository;
import tghtechnology.chozaazul.Repository.PlatoRepository;
import tghtechnology.chozaazul.Utils.Exceptions.IdNotFoundException;
import tghtechnology.chozaazul.dto.MenuCumple.MenuCumpleDTOForInsert;
import tghtechnology.chozaazul.dto.MenuCumple.MenuCumpleDTOForList;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOForList;

@Service
@AllArgsConstructor
public class MenuService {

	@Autowired
	private PlatoService plaService;
    private PlatoRepository plaRepository;
	private MenuRepository menuRepo;

	/**
	 * Listar todos los menus de cumpleaños
	 * @return Lista de menus 
	 */
	public List<MenuCumpleDTOForList> listar(){
		final List<MenuCumpleDTOForList> menus = new ArrayList<>();
		
		List<MenuCumple> mcs = menuRepo.listarMenuCumple();
		mcs.forEach( mc -> menus.add(new MenuCumpleDTOForList(mc)));
		
		return menus;
	}
	
	/**
	 * Listar los menus por mes
	 * @param mes El mes del cual listar los menus
	 * @return Lista de menus en formato ForList
	 */
	public List<MenuCumpleDTOForList> listarPorMes(Mes mes){
		final List<MenuCumpleDTOForList> menus = new ArrayList<>();
		
		List<MenuCumple> mcs = menuRepo.listarPorMes(mes);
		mcs.forEach( mc -> menus.add(new MenuCumpleDTOForList(mc)));
		
		return menus;
	}

	
 	/**
     * Listar un solo menu mediante su ID
     * @param id La ID a buscar
     * @return El menu encontrado
     * @throws IdNotFoundException Si no encuentra ningún menu
     */
    public MenuCumpleDTOForList listarUno(Integer id) throws IdNotFoundException{
    	MenuCumple menu = buscarPorId(id);
        return new MenuCumpleDTOForList(menu);
    }
    
    /**
     * Crear un nuevo menu de cumpleaños
     * @param imc Menu para insertar en formato ForInsert
     * @param imagen Imagen para asignar al plato
     * @return El menu creado en formato ForList
     * @throws IOException
     */
    public MenuCumpleDTOForList crearMenuCumple(MenuCumpleDTOForInsert imc, MultipartFile imagen) throws IOException{
 
    	MenuCumple mc = new MenuCumple();
    	mc.setMes(imc.getMes());
    	
    	PlatoDTOForList lpl = plaService.crearPlato(imc.getPlato(), imagen);
    	Plato pl = plaRepository.findById(lpl.getId_plato()).get();
    	
    	mc.setPlato(pl);
    	mc.setEstado(true);
    	
    	mc = menuRepo.save(mc);
    	return new MenuCumpleDTOForList(mc);
    	
    }
    
    /**
     * Actualizar un menu de cumpleaños
     * @param id ID del menu a modificar
     * @param body Datos del menu a modificar en formato ForInsert
     * @param imagen Imagen que asignar al plato (opcional)
     * @throws IOException
     */
    public void actualizarMenu(Integer id, MenuCumpleDTOForInsert body, MultipartFile imagen) throws IOException{
    	
    	MenuCumple mc = buscarPorId(id);
    	mc.setMes(body.getMes());
    	
    	plaService.actualizarPlato(mc.getPlato().getId_plato(), body.getPlato(), imagen);
    	
    	menuRepo.save(mc);
    }
    
    /**
     * Eliminar un menu
     * @param id ID del menu a eliminar
     */
    public void eliminarMenu(Integer id){
    	MenuCumple mc = buscarPorId(id);
    	mc.setEstado(false);
    	
    	plaService.eliminarPlato(mc.getPlato().getId_plato());
    	menuRepo.save(mc);
    	
    }

    private MenuCumple buscarPorId(Integer id) {
		return menuRepo.listarUno(id).orElseThrow( () -> new IdNotFoundException("menu de cumple"));
	}
    
}
