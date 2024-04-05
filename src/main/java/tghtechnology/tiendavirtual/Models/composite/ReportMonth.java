package tghtechnology.tiendavirtual.Models.composite;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.Mes;

@Getter
@Setter
@Embeddable
public class ReportMonth {

	private short year;
	private Mes month;
	
}
