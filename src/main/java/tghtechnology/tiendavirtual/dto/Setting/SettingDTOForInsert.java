package tghtechnology.tiendavirtual.dto.Setting;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Setting;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;

@Getter
@Setter
@NoArgsConstructor
public class SettingDTOForInsert implements DTOForInsert<Setting>{

	@NotEmpty(message = "No puede estar vacío")
	private String identificador;
	
	@NotEmpty(message = "No puede estar vacío")
	private String valor;
	
	@Override
	public Setting toModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Setting updateModel(Setting set) {
		if(!set.isEditable())
			throw new DataMismatchException("setting","La opción no es modificable");
		set.setBaseValue(valor);
		return set;
	}

}
