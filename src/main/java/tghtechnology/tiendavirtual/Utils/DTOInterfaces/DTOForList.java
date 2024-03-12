package tghtechnology.tiendavirtual.Utils.DTOInterfaces;

public interface DTOForList<T> {

	/**
	 * Crea un DTO de tipo ForList desde el modelo que representa
	 * @param model El modelo a partir del cual se generará el DTO
	 * @return el DTO ForList generado
	 */
	public DTOForList<T> from(T model);
	
}
