package tghtechnology.tiendavirtual.Utils.Cloudinary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary("cloudinary://269427965692487:dPIsYT7kWdJoftwRYo_zGH1nYp4@dryp7amgv");
	}
	
}
