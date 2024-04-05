package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.composite.ReportMonth;

@Entity
@Table(name = "monthly_report")
@Getter
@Setter
public class ReporteMensual {

    @EmbeddedId
    private ReportMonth id;
    
    private BigDecimal totalIngresos;
    
    private BigDecimal totalGanancias;
    
    private Integer numeroVentas;
    
    private Integer numProductosVendidos;
    
    @OneToMany(mappedBy = "reporte")
    private Set<ItemReporte> relevant_items = new HashSet<>();
    
}
