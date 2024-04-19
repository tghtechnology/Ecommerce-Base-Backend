package tghtechnology.tiendavirtual.Utils.Cloudinary;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import tghtechnology.tiendavirtual.Enums.TipoImagen;
import tghtechnology.tiendavirtual.Models.Imagen;

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
	public Imagen subirImagenMarca(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource = uploadExt(name, "marcas", file, new SizeTransformation(256, 128, "fit"));
		img.setImagen(resource.get("url").toString());
		img.setId_imagen(resource.get("public_id").toString());
		return toImagen(img, TipoImagen.MARCA);
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
	public Imagen subirImagenAnuncio(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource = uploadExt(name, "anuncios", file, new SizeTransformation(1280, 720, "scale"));
		img.setImagen(resource.get("url").toString());
		img.setId_imagen(resource.get("public_id").toString());
		return toImagen(img, TipoImagen.ANUNCIO);
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
	public Imagen subirImagenRedSocial(String name, MultipartFile file) throws IOException {
		Img img = new Img();
		Map<?, ?> resource = uploadExt(name, "redes", file, new SizeTransformation(128, 128, "thumb", Gravity.AUTO));
		img.setImagen(resource.get("url").toString());
		img.setId_imagen(resource.get("public_id").toString());
		
		return toImagen(img, TipoImagen.RED_SOCIAL);
	}
	
	/**
	 * Sube dos imagenes a Cloudinary:<br>
	 * - La imagen completa en la carpeta "productos/full", tamaño full<br>
	 * - La miniatura de la imagen en la carpeta "productos/thumbs", tamaño 264x177<br>
	 * 
	 * @param name Nombre del archivo
	 * @param file Archivo de imagen
	 * @return Los enlaces de la imagen en un objeto Img
	 * @throws IOException
	 */
	public Imagen subirImagenItem(String name, MultipartFile file) throws IOException {
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
		
		name = name + "-" + now;
		
		Img img = new Img();
		Map<?, ?> resource1 = upload(name, "productos/full", file);
		img.setImagen(resource1.get("url").toString());
		img.setId_imagen(resource1.get("public_id").toString());
		
		Map<?, ?> resource2 = uploadExt(name, "productos/thumbs", file, new SizeTransformation(177, 264, "thumb"));
		img.setMiniatura(resource2.get("url").toString());
		img.setId_miniataura(resource2.get("public_id").toString());
		return toImagen(img, TipoImagen.PRODUCTO);
	}
	
	/**
	 * Sube dos imagenes a Cloudinary:<br>
	 * - La imagen completa en la carpeta "variaciones/full", tamaño full<br>
	 * - La miniatura de la imagen en la carpeta "variaciones/thumbs", tamaño 264x177<br>
	 * 
	 * @param name Nombre del archivo
	 * @param file Archivo de imagen
	 * @return Los enlaces de la imagen en un objeto Img
	 * @throws IOException
	 */
	public Imagen subirImagenVariacion(String name, MultipartFile file) throws IOException {
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
		
		name = name + "-" + now;
		
		Img img = new Img();
		Map<?, ?> resource1 = upload(name, "productos/full", file);
		img.setImagen(resource1.get("url").toString());
		img.setId_imagen(resource1.get("public_id").toString());
		
		Map<?, ?> resource2 = uploadExt(name, "productos/thumbs", file, new SizeTransformation(177, 264, "thumb"));
		img.setMiniatura(resource2.get("url").toString());
		img.setId_miniataura(resource2.get("public_id").toString());
		return toImagen(img, TipoImagen.VARIACION);
	}
	
	/**
	 * Elimina una lista de imágenes de cloudinary
	 * 
	 * @param public_ids Lista de ids públicas de las imágenes a borrar
	 * @return Si se eliminó con éxito
	 * @throws Exception
	 */
	public boolean eliminarImagenes(List<String> public_ids) throws Exception {
		
		ApiResponse response =  cloudinary.api().deleteResources(public_ids, ObjectUtils.emptyMap());
		//System.out.println(response);
		return !((boolean) response.get("partial"));
	}
	
	/**
	 * Elimina una lista de imágenes de cloudinary
	 * 
	 * @param public_id Las IDs públicas de las imágenes a borrar
	 * @return Si se eliminó con éxito
	 * @throws Exception
	 */
	public boolean eliminarImagenes(String... public_ids) throws Exception {
		return eliminarImagenes(Arrays.asList(public_ids));
	}
	
	private Map<?, ?> upload(String name, String folder, MultipartFile file) throws IOException{
		return uploadExt(name, folder, file, null);
	}

	private Map<?, ?> uploadExt(String name, String folder, MultipartFile file, SizeTransformation transformation) throws IOException{
		
		Map<String, Object> options = new HashMap<>();
		options.put("public_id", name);
		options.put("folder", master_directory + folder);
		
		if(transformation != null)
			options.put("transformation", transformation.build());
		
		return cloudinary.uploader().upload(convertToBase64Uri(file) ,options);
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
	
	private Imagen toImagen(Img img, TipoImagen tipo) {
		Imagen imagen = new Imagen();
		imagen.setImagen(img.getImagen());
		imagen.setMiniatura(img.getMiniatura());
		imagen.setPublic_id_Imagen(img.getId_imagen());
		imagen.setPublic_id_Miniatura(img.getId_miniataura());
		imagen.setTipo(tipo);
		return imagen;
	}
	
}
