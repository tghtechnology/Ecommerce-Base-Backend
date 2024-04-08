package tghtechnology.tiendavirtual.Models.composite;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.Mes;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ReportMonth {

	private short year;
	private Mes month;
	
}
