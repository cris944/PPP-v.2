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
@Table(name="detalle_ppp")
@CrossOrigin
public class Detalle_PPP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle_ppp")
    private Long id_procesos;

    @ManyToOne
    @JoinColumn(name = "id_ppp")
    private PPP ppp;

    @ManyToOne
    @JoinColumn(name = "id_procesos")
    private Procesos procesos;

    @ManyToOne
    @JoinColumn(name = "id_carrera_plan")
    private Carrera_Plan carrera_plan;

    @ManyToOne
    @JoinColumn(name = "id_requisito")
    private Requisitos requisitos;

    @ManyToOne
    @JoinColumn(name = "Responsable")
    private Persona persona;

    @Column(name="orden")
    private Number orden;

    @Column(name="estado" , length = 1)
    private char estado;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="detalle_ppp")
	@JsonIgnore
	private Set<Detalle_Documento> detalle_documento;
}
