package tghtechnology.tiendavirtual.dto.Setting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.SettingType;
import tghtechnology.tiendavirtual.Models.Setting;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class SettingDTOForList implements DTOForList<Setting>{

	private String identificador;
	private String valor;
	private String valor_por_defecto;
	private SettingType tipo;
	private Boolean modificable;
	
	@Override
	public SettingDTOForList from(Setting set) {
		this.identificador = set.getId();
		this.valor = set.getValor();
		this.valor_por_defecto = set.getBaseValue();
		this.tipo = set.getType();
		this.modificable = set.isEditable();
		return this;
	}

}
