package tghtechnology.tiendavirtual.Utils.DTOInterfaces;

public interface DTOForInsert<T> {

	/**
	 * Convierte el DTO ForInsert en el modelo que representa
	 * @return Un modelo nuevo
	 */
	public T toModel();
	
	/**
	 * Utiliza los datos del DTO ForInsert para modificar un modelo
	 * @param modelToUpdate El modelo a modificar
	 * @return El modelo modificado
	 */
	public T updateModel(T modelToUpdate);
	
}
