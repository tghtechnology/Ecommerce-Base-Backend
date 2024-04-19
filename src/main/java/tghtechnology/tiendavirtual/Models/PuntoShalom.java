package tghtechnology.tiendavirtual.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.RegionPeru;

@Entity
@Table(name = "puntoshalom")
@Getter
@Setter
public class PuntoShalom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_punto;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @Column(nullable = false)
    private String lugar;
    
    @Column(nullable = false)
    private RegionPeru departamento;
    
    @Column(nullable = false)
    private String provincia;
    
    @Column(nullable = false)
    private String distrito;
    
    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private Boolean activo;
    
    @Column(nullable = false)
    private Boolean estado;
    
}
