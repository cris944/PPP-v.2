package pe.edu.upeu.pppmanager.entity;



import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Data
@Table(name = "estudiante")
@CrossOrigin
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long id;
    @Column(name = "codigo", length = 9, nullable = false, unique = true)
    private String codigo;
    
    @Column(name = "ciclo", length = 100)
    private String ciclo;
    
    @Column(name = "grupo", length = 100)
    private String grupo;
    
    @Column(name = "correo_institucional", length = 100, unique = true)
    private String correo_institucional;
    
	@ManyToOne
	@JoinColumn(name="id_persona", nullable = false)
	private Persona persona;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="estudiante",orphanRemoval = true)
	@JsonIgnore
	private Set<Matricula> matricula;
}
