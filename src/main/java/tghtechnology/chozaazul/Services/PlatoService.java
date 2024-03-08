package tghtechnology.chozaazul.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Models.Categoria;
import tghtechnology.chozaazul.Models.Plato;
import tghtechnology.chozaazul.Models.Enums.TipoPlato;
import tghtechnology.chozaazul.Repository.CategoriaRepository;
import tghtechnology.chozaazul.Repository.PlatoRepository;
import tghtechnology.chozaazul.Utils.Cloudinary.Img;
import tghtechnology.chozaazul.Utils.Cloudinary.MediaManager;
import tghtechnology.chozaazul.Utils.Exceptions.IdNotFoundException;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOForInsert;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOForList;

@Service
@AllArgsConstructor
public class PlatoService {

	@Autowired
    private PlatoRepository plaRepository;
	private CategoriaRepository catRepo;
	private MediaManager mediaManager;

    /*Listar plato*/
    public List<PlatoDTOForList> listarPlato (String query,
    										  BigDecimal min,
    										  BigDecimal max,
    										  String categoria,
    										  TipoPlato tipoPlato
    		){
        List<PlatoDTOForList> platoList = new ArrayList<>();
        List<Plato> plas = (List<Plato>) plaRepository.listar(query, min, max, categoria, tipoPlato);
        
        plas.forEach( x -> {
            platoList.add(new PlatoDTOForList(x));
        });
        return platoList;
    }
    
    /*Obtener un plato especifico*/
    public PlatoDTOForList listarUno( Integer id){
    	Plato plato = buscarPorId(id);
        return new PlatoDTOForList(plato);
    }
    
    /*Obtener un plato especifico*/
    public PlatoDTOForList listarUno(String text_id){
    	Plato plato = buscarPorId(text_id);
        return new PlatoDTOForList(plato);
    }
    
    /**Registrar nuevo plato
     * @throws IOException */
    public PlatoDTOForList crearPlato(PlatoDTOForInsert iPla, MultipartFile imagen) throws IOException{
 
    	//Obtener tipo de plato
    	String text_id = Plato.transform_id(iPla.getNombre_plato());
    	
    	if(plaRepository.listarUno(text_id).isEmpty()) {
    	
    		Categoria cat = obtenerCategoria(iPla.getId_categoria());
    		
	    	//Crear nuevo plato
	    	Plato pla = new Plato();
	    	pla.setText_id(text_id);
	    	pla.setNombre_plato(iPla.getNombre_plato());
	        pla.setDescripcion(iPla.getDescripcion());
	        pla.setPrecio(iPla.getPrecio());
	        pla.setTipoPlato(iPla.getTipo_plato());
	        pla.setDisponibilidad(iPla.getDisponibilidad());
	        pla.setFecha_creacion(LocalDateTime.now());
	        pla.setFecha_modificacion(LocalDateTime.now());
	        pla.setEstado(true);
	        
	        pla.setCategoria(cat);
	        
	        Img img = mediaManager.subirImagenProducto(text_id, imagen);
	        
	        pla.setImagen_primaria(img.getImg_primaria());
	        pla.setImagen_secundaria(img.getImg_secundaria());
	        
	        plaRepository.save(pla);
	        return new PlatoDTOForList(pla);
    	} else {
    		throw new DataIntegrityViolationException("El nombre (" + text_id + ") ya existe para producto.");
    	}
    }
    
    /*Actualizar plato */
    public void actualizarPlato(Integer id, PlatoDTOForInsert body, MultipartFile imagen) throws IOException{
    	
    	Plato plato = buscarPorId(id);
    	plato.setNombre_plato(body.getNombre_plato());
        plato.setDescripcion(body.getDescripcion());
        plato.setPrecio(body.getPrecio());
        plato.setTipoPlato(body.getTipo_plato());
        plato.setDisponibilidad(body.getDisponibilidad());
        plato.setFecha_modificacion(LocalDateTime.now());
        
        if(imagen != null) {
	        Img img = mediaManager.subirImagenProducto(plato.getText_id(), imagen);
	        
	        plato.setImagen_primaria(img.getImg_primaria());
	        plato.setImagen_secundaria(img.getImg_secundaria());
        }
        plaRepository.save(plato);
    }
    
    /**Eliminar plato */
    public void eliminarPlato(Integer id){
    	Plato pla = buscarPorId(id);
        pla.setEstado(false);
        pla.setText_id(pla.getId_plato() + "%DELETED%" + pla.getText_id());
        plaRepository.save(pla);
    }
    
    private Plato buscarPorId(Integer id) {
		return plaRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("plato"));
	}
    private Plato buscarPorId(String text_id) {
		return plaRepository.listarUno(text_id).orElseThrow( () -> new IdNotFoundException("plato"));
	}  
    
	private Categoria obtenerCategoria(int id) {
		return catRepo.listarUno(id).orElseThrow(() -> new IdNotFoundException("categoria"));
	}

}
