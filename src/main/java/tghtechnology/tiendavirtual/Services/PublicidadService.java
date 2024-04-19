package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Publicidad;
import tghtechnology.tiendavirtual.Repository.ImagenRepository;
import tghtechnology.tiendavirtual.Repository.PublicidadRepository;
import tghtechnology.tiendavirtual.Utils.Cloudinary.MediaManager;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Publicidad.PublicidadDTOForInsert;
import tghtechnology.tiendavirtual.dto.Publicidad.PublicidadDTOForList;

@Service
@AllArgsConstructor
public class PublicidadService {

    private PublicidadRepository pubRepository;
    private ImagenRepository imaRepository;
    
    private MediaManager mediaManager;
    
    /**
     * Lista todas las publicidades no eliminadas.<br>
     * Si el usuario no tiene autenticación o no es gerente, 
     * solo muestra las publicidades activas.<br>
     * Si el usuario tiene autenticación de gerente, se puede elegir 
     * si mostrar las publicidades desactivadas o no.
     * 
     * @param mostrar Si mostrar o no las publicidades desactivadas (No eliminadas).
     * @param auth La autenticación del usuario.
     * 
     * @return Una lista de las publicidades en formato ForList.
     */
    public List<PublicidadDTOForList> listarPublicidad (Boolean mostrar, Authentication auth){
        List<Publicidad> publ;
        
        if(!checkPermitted(auth))
        	publ = pubRepository.listar(true);
        else
        	if(mostrar == null)
        		publ = pubRepository.listar();
        	else
        		publ = pubRepository.listar(mostrar);
        
        return publ.stream().map(p -> new PublicidadDTOForList().from(p)).collect(Collectors.toList());
    }
    
    /**
     * Obtiene una publicidad en específico según su ID
     * 
     * @param id la ID de la publicidad
     * @return la publicidad encontrada en formato ForList o null si no existe
     */
    public PublicidadDTOForList listarUno(Integer id){
        Publicidad pub = pubRepository.listarUno(id).orElse(null);
        return pub == null ? null : new PublicidadDTOForList().from(pub);
    }
    
    /**
     * Registra una nueva publicidad
     * 
     * @param iPub Publicidad en formato ForInsert
     * @return la publicidad creada en formato ForList
     * @throws IOException 
     */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public PublicidadDTOForList crearPublicidad(PublicidadDTOForInsert iPub, MultipartFile imagen) throws IOException{
        Publicidad pub = iPub.toModel();
        pub = pubRepository.save(pub);
        
        Imagen img = mediaManager.subirImagenAnuncio(pub.get_img_id(), imagen);
        img.setId_owner(pub.getId_publicidad());
		imaRepository.save(img);
		
		pub.setImagen(img);
		pub = pubRepository.save(pub);
        
        return new PublicidadDTOForList().from(pub);
    }
    
    /**
     * Modifica una publicidad
     * 
     * @param id ID de la publicidad a modificar
     * @param mPub Datos de la publicidad en formato ForInsert
     * @throws Exception Si hay algun error al eliminar/subir la imagen a Cloudinary
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna publicidad
     */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public void actualizarPublicidad(Integer id, PublicidadDTOForInsert mPub, MultipartFile imagen) throws Exception{
        Publicidad pub = buscarPorId(id);
        pub = mPub.updateModel(pub);
        
		if (imagen != null) {
			// Eliminar imagen anterior
			mediaManager.eliminarImagenes(pub.getImagen().getPublic_id_Imagen());
			imaRepository.delete(pub.getImagen());
			
			// Subir la nueva imagen
			Imagen img = mediaManager.subirImagenAnuncio(pub.get_img_id(), imagen);
			img.setId_owner(pub.getId_publicidad());
			imaRepository.save(img);
			pub.setImagen(img);
		}
        
        pubRepository.save(pub);
    }
    
    /**
     * Cambia si mostrar o no una publicidad
     * 
     * @param id ID de la publicidad a modificar
     * @param mostrar Si nostrar o no la publicidad
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna publicidad
     */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public void actualizarMostrarPublicidad(Integer id, Boolean mostrar) throws Exception{
        Publicidad pub = buscarPorId(id);
        pub.setMostrar(mostrar);
        pubRepository.save(pub);
    }
    
    /**
     * Realiza un eliminado lógico de una publicidad
     * 
     * @param id ID de la publicidad a eliminar
     * @throws Exception 
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna publicidad
     */
    @Transactional(rollbackFor = {IdNotFoundException.class, IOException.class, DataIntegrityViolationException.class})
    public void eliminarPublicidad(Integer id) throws Exception{
        // Eliminar publicidad
    	Publicidad pub = buscarPorId(id);
    	Imagen img = pub.getImagen();
    	
        pub.setEstado(false);
        pub.setImagen(null);
        pubRepository.save(pub);
        
        //Eliminar la imagen
		imaRepository.delete(img);
		mediaManager.eliminarImagenes(img.getPublic_id_Imagen());
    }
    
    
    public Publicidad buscarPorId(Integer id) throws IdNotFoundException{
		return pubRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("publicidad"));
	}
    
    private boolean checkPermitted(Authentication auth) {
    	return auth != null && TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE);
    }
}