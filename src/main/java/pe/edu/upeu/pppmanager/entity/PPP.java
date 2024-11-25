package pe.edu.upeu.pppmanager.entity;


import java.util.Date;
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
@Table(name = "PPP")
@CrossOrigin

public class PPP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ppp")
    private Long id_ppp;

    @ManyToOne
    @JoinColumn(name = "id_supervisor_academico")
    private Persona supervisor_academico;

    @ManyToOne
    @JoinColumn(name = "id_coordinador_academico")
    private Persona coordinador_academico;

    @ManyToOne
    @JoinColumn(name = "id_matricula")
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;

    @Column(name="fecha_inicio")
    private Date fecha_inicio;

    @Column(name="fecha_final")
    private Date fecha_final;

    @Column(name="horas_realizadas")
    private Number horas_realizadas;

    @Column(name="promedio")
    private Double promedio;

    @Column(name="estado" , length = 1, nullable = false)
    private char estado;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="ppp")
	@JsonIgnore
	private Set<Detalle_PPP> detalle_ppp;
}
