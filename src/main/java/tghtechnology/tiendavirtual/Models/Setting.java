package tghtechnology.tiendavirtual.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.SettingType;

@Entity
@Table(name = "page_settings")
@Getter
@Setter
@AllArgsConstructor
public class Setting {

	@Id
	private String key;
	
	@Column(nullable = false)
	private String value;
	
	@Column(nullable = false)
	private String default_value;
  	
	@Column(nullable = false)
	private SettingType type;
	
	@Column(nullable = false)
	private boolean editable = true;
	
}
