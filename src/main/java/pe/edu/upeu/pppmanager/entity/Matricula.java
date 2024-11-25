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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="matricula")
@CrossOrigin
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_matricula")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_carrera_plan")
    private Carrera_Plan carrera_plan;

    @Column(name="horas_ppp")
    private Number horas_ppp;

    @Column(name="horas_vinculacion_medio")
    private Number horas_vinculacion_medio;
    
    @Column(name="contrato",length = 100)
    private String contrato;
    
    @Column(name="modalidad",length = 100)
    private String modalidad;

    @Column(name = "estado",length = 1, nullable = false) 
    private char estado;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="matricula",orphanRemoval = true)
	@JsonIgnore
	private Set<PPP> ppp;
}

