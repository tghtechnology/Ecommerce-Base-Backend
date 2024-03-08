package tghtechnology.tiendavirtual.Utils.Cloudinary;

import com.cloudinary.Transformation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeTransformation{
	private int width;
	private int height;
	private String crop;
	private Gravity gravity;
	
	public SizeTransformation(int width, int height, String crop, Gravity gravity) {
		super();
		this.width = width;
		this.height = height;
		this.crop = crop;
		this.gravity = gravity;
	}
	
	public SizeTransformation(int width, int height, String crop) {
		super();
		this.width = width;
		this.height = height;
		this.crop = crop;
		this.gravity = Gravity.CENTER;
	}
	
	public Transformation<?> build() {
		return new Transformation<>()
				.width(this.width)
				.height(this.height)
				.crop(this.crop);
	}
	
}
