package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Repository.ImagenRepository;
import tghtechnology.tiendavirtual.Repository.MarcaRepository;
import tghtechnology.tiendavirtual.Utils.Cloudinary.MediaManager;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForList;

@Service
@AllArgsConstructor
public class MarcaService {

    private MarcaRepository marRepository;
	private ImagenRepository imaRepository;
	
	private MediaManager mediaManager;

    /**
     * Lista todas las marcas no eliminadas
     * @return Una lista de las marcas en formato ForList
     */
    public List<MarcaDTOForList> listarMarcas (){
        List<MarcaDTOForList> marcaList = new ArrayList<>();
        List<Marca> mars = (List<Marca>) marRepository.listar();
        
        mars.forEach( x -> {
        	marcaList.add(new MarcaDTOForList().from(x));
        });
        return marcaList;
    }
    
    /**
     * Obtiene una marca en específico según su ID de texto
     * @param id la ID de texto de la marca
     * @return la marca encontrada en formato ForList o null si no existe
     */
    public MarcaDTOForList listarUno(String text_id){
        Marca mar = marRepository.listarUno(text_id).orElse(null);
        return mar == null ? null : new MarcaDTOForList().from(mar);
    }
    
    /**
     * Obtiene una marca en específico según su ID
     * @param id la ID de la marca
     * @return la marca encontrada en formato ForList o null si no existe
     */
    public MarcaDTOForList listarUnoPorID(Integer id){
        Marca mar = marRepository.listarUno(id).orElse(null);
        return mar == null ? null : new MarcaDTOForList().from(mar);
    }
    
    /**
     * Registra una nueva marca
     * @param Marca en formato ForInsert
     * @return La marca creada en formato ForList
     * @throws IOException 
     */
    @Transactional(rollbackFor = {IOException.class, DataIntegrityViolationException.class})
    public MarcaDTOForList crearMarca(MarcaDTOForInsert iMar, MultipartFile imagen) throws IOException{
        Marca mar = iMar.toModel();
        marRepository.save(mar);
        
        if(imagen != null) {
	        Imagen img = mediaManager.subirImagenMarca(mar.getText_id(), imagen);
	        img.setId_owner(mar.getId_marca());
			img = imaRepository.save(img);
			
			mar.setLogo(img);
			marRepository.save(mar);
        }
        return new MarcaDTOForList().from(mar);
    }
    
    /**
     * Modifica una marca
     * @param id ID de la marca a modificar
     * @param mCat Datos de la marca en formato ForInsert
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna marca
     */
    public void actualizarMarca(Integer id, MarcaDTOForInsert mMar){
        Marca mar = buscarPorId(id);
        mar = mMar.updateModel(mar);
        marRepository.save(mar);
    }
    
    /**
     * Realiza un eliminado lógico de una marca
     * @param id ID de la marca a eliminar
     * @throws IdNotFoundException Si la ID proporcionada no corresponde a ninguna marca
     */
    public void eliminarMarca(Integer id){
        Marca mar = buscarPorId(id);
        mar.setEstado(false);
        mar.setText_id(mar.getId_marca() + "%DELETED%" + mar.getText_id());
        marRepository.save(mar);
    }
    
    
    public Marca buscarPorId(Integer id) throws IdNotFoundException{
		return marRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("marca"));
	}
}