package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;

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
@Table(name = "valoracion")
@Getter
@Setter
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_valoracion;
    
    @Column(nullable = false)
    private Short estrellas;
    
    @Column(nullable = true, length = 1000)
    private String comentario;
    
    @Column(nullable = false)
    private LocalDateTime fecha_creacion;
    
    @Column(nullable = true)
    private LocalDateTime fecha_modificacion;
    
    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
    
    @ManyToOne
	@JoinColumn(name = "id_item")
	private Item item;
    
}
