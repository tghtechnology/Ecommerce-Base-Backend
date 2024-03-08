package tghtechnology.chozaazul.Utils.Cloudinary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class MediaManager {

	@Autowired
	private Cloudinary cloudinary;
	
	@Value("${cloudinary.directory}")
	private String master_directory;
	
	/**
	 * Sube una imagen a Cloudinary dentro de
	 * la carpeta "marcas" con un tamaño de 128x128
	 * 
	 * @param name Nombre del archivo
	 * @param file Archivo de imagen
	 * @return Los enlaces de la imagen en un objeto Img
	 * @throws IOException
	 */
	public Img subirImagenMarca(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource = uploadExt(name, "marcas", file, new SizeTransformation(128, 128, "thumb"));
		img.setImg_primaria(resource.get("url").toString());
		return img;
	}
	
	/**
	 * Sube una imagen a Cloudinary dentro de
	 * la carpeta "anuncios" con un tamaño de 1280x720
	 * 
	 * @param name Nombre del archivo
	 * @param file Archivo de imagen
	 * @return Los enlaces de la imagen en un objeto Img
	 * @throws IOException
	 */
	public Img subirImagenAnuncio(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource = uploadExt(name, "anuncios", file, new SizeTransformation(1280, 720, "scale"));
		img.setImg_primaria(resource.get("url").toString());
		return img;
	}
	
	/**
	 * Sube una imagen a Cloudinary dentro de
	 * la carpeta "redes" con un tamaño de 128x128 y centrado en el logo
	 * 
	 * @param name Nombre del archivo
	 * @param file Archivo de imagen
	 * @return Los enlaces de la imagen en un objeto Img
	 * @throws IOException
	 */
	public Img subirImagenRedSocial(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource = uploadExt(name, "redes", file, new SizeTransformation(128, 128, "thumb", Gravity.AUTO));
		img.setImg_primaria(resource.get("url").toString());
		return img;
	}
	
	/**
	 * Sube dos imagenes a Cloudinary:
	 * - La imagen completa en la carpeta "productos/full", tamaño full
	 * - La miniatura de la imagen en la carpeta "productos/thumbs", tamaño 264x177
	 * 
	 * @param name Nombre del archivo
	 * @param file Archivo de imagen
	 * @return Los enlaces de la imagen en un objeto Img
	 * @throws IOException
	 */
	public Img subirImagenProducto(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource1 = upload(name, "productos/full", file);
		img.setImg_primaria(resource1.get("url").toString());
		
		Map<?, ?> resource2 = uploadExt(name, "productos/thumbs", file, new SizeTransformation(264, 177, "thumb"));
		img.setImg_secundaria(resource2.get("url").toString());
		return img;
	}
	
	private Map<?, ?> upload(String name, String folder, MultipartFile file) throws IOException{
		return uploadExt(name, folder, file, null);
	}

	private Map<?, ?> uploadExt(String name, String folder, MultipartFile file, SizeTransformation transformation) throws IOException{
		
		Map<String, Object> options = new HashMap<>();
		options.put("public_id", name);
		System.out.println("folder: " + master_directory);
		options.put("folder", master_directory + folder);
		
		if(transformation != null)
			options.put("transformation", transformation.build());
		
		return cloudinary.uploader().upload(convertToBase64Uri(file), options);
	}
	

	private String convertToBase64Uri(MultipartFile file) {
	    String uri = "";
	    try{
	        byte[] bytes = Base64.encodeBase64(file.getBytes());
	        String result = new String(bytes);
	        uri = "data:" + file.getContentType() + ";base64," + result;
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return  uri;
    
	}
	
}
