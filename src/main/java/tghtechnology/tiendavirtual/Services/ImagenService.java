package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoImagen;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Repository.ImagenRepository;
import tghtechnology.tiendavirtual.Utils.Cloudinary.MediaManager;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;

@Service
@AllArgsConstructor
public class ImagenService {
	
	@Autowired
	private ImagenRepository imgRepo;
	private MediaManager mediaManager;
	
	// Eliminar
	public void eliminarImagenes(TipoImagen tipo,Integer id_owner) throws Exception {
		
		final List<Imagen> imgs = buscar(tipo, id_owner);
		final List<String> public_ids = new ArrayList<>();
		
		
		imgs.forEach( img ->{
			public_ids.add(img.getPublic_id_Imagen());
			if(img.getPublic_id_Miniatura() != null)
				public_ids.add(img.getPublic_id_Miniatura());
		});
		
		try {
			boolean resp = mediaManager.eliminarImagenes(public_ids);
			if(!resp)
				throw new Exception("Error al eliminar las imagenes");
		} catch (Exception e) {
			Exception ex = new Exception("Error al eliminar las imagenes");
			ex.setStackTrace(e.getStackTrace());
			throw ex;
		}
		
	}
	
	// Eliminar Una
	public void eliminarImagen(Integer id) throws Exception {
		
		final Imagen img = buscarPorId(id);
		final List<String> public_ids = new ArrayList<>();
		
		public_ids.add(img.getPublic_id_Imagen());
		if(img.getPublic_id_Miniatura() != null)
			public_ids.add(img.getPublic_id_Miniatura());

		
		try {
			boolean resp = mediaManager.eliminarImagenes(public_ids);
			if(!resp)
				throw new Exception("Error al eliminar la imagen");
		} catch (Exception e) {
			Exception ex = new Exception("Error al eliminar la imagen");
			ex.setStackTrace(e.getStackTrace());
			throw ex;
		}
		imgRepo.delete(img);
	}
	
	private Imagen buscarPorId(int id) {
		return imgRepo.findById(id).orElseThrow(() -> new IdNotFoundException("imagen"));
	}
	
	private List<Imagen> buscar(TipoImagen tipo, Integer id_owner) {
		
		List<Imagen> imgs = imgRepo.listarPorObjeto(tipo, id_owner);
		if(imgs.isEmpty()) throw new IdNotFoundException("imagen");
		return imgs;
	}
	
}
