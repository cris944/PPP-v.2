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
@Table(name="carrera_plan")
@CrossOrigin
public class Carrera_Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_carrera_plan")
    private Long id_carrera_plan;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carreras;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan planes;

    @Column(name = "estado",length = 1, nullable = false) 
    private char estado;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="carrera_plan")
	@JsonIgnore
	private Set<Detalle_PPP> detalle_ppp;
	
	
}
