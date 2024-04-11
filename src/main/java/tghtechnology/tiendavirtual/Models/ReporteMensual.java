package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
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
    
    @Column(nullable = false)
    private BigDecimal totalIngresos;
    
    @Column(nullable = false)
    private BigDecimal totalEgresos;
    
    @Column(nullable = false)
    private BigDecimal totalImpuestos;
    
    @Column(nullable = false)
    private Integer numeroVentas;
    
    @Column(nullable = false)
    private Integer numItemsVendidos;
    
    @OneToMany(mappedBy = "reporte")
    private Set<ReporteItem> relevant_items = new HashSet<>();
    
}
