package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DistritoLima;

@Getter
@Setter
@Entity
@Table(name = "distrito")
public class DistritoDelivery {

	@Id
	private DistritoLima id_distrito;
	
	@Column(nullable = false)
	private BigDecimal precio_delivery;
	
	@Column(nullable = false)
	private Boolean activo;
	
}
