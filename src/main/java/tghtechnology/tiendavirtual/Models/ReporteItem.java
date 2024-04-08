package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report_item")
@Getter
@Setter
public class ReporteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable = false)
    private Boolean topSeller = false;
    
    @Column(nullable = false)
    private Boolean topEarner = false;
    
    @Column(nullable = false)
    private Integer ventas = 0;
    
    @Column(nullable = false)
    private BigDecimal ingresos = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private BigDecimal ganancias = BigDecimal.ZERO;
    
    @ManyToOne
    @JoinColumns({
    	@JoinColumn(name="reporte_year", referencedColumnName="year"),
    	@JoinColumn(name="reporte_month", referencedColumnName="month")
    })
    private ReporteMensual reporte;
    
    @OneToOne
    @JoinColumn(name = "id_item")
    private Item item;
    
}
