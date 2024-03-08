package tghtechnology.tiendavirtual.Utils.Cloudinary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	@Value("${cloudinary.url}")
	private String url;
	
	@Bean
	public Cloudinary cloudinary() {
		
		return new Cloudinary(url);
	}
	
}
