package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report_item")
@Getter
@Setter
public class ItemReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable = false)
    private Boolean topSeller;
    
    @Column(nullable = false)
    private Boolean topEarner;
    
    @Column(nullable = false)
    private Integer ventas;
    
    @Column(nullable = false)
    private BigDecimal ingresos;
    
    @Column(nullable = false)
    private BigDecimal ganancias;
    
    @ManyToOne
    @JoinColumn(name = "id_reporte")
    private ReporteMensual reporte;
    
}
