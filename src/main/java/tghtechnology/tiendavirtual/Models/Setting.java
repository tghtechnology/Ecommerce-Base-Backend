package tghtechnology.tiendavirtual.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.SettingType;

@Entity
@Table(name = "page_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setting {

	@Id
	private String id;
	
	@Column(nullable = false)
	private String valor;
	
	@Column(nullable = false)
	private String baseValue;
  	
	@Column(nullable = false)
	private SettingType type;
	
	@Column(nullable = false)
	private boolean editable = true;
	
}
