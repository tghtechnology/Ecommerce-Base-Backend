package tghtechnology.chozaazul.Utils.Cloudinary;

import lombok.Getter;

public enum ImgSource {
	
	PRODUCTO("productos/"),
	MARCA("marcas/");
	
	@Getter
	private String folder;
	
	private ImgSource(String folder) {
		this.folder = folder;
	}

}
