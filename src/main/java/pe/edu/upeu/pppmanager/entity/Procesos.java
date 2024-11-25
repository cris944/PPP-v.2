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
@Table(name="procesos")
@CrossOrigin
public class Procesos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_procesos")
    private Long id_procesos;
    @Column(name="nombre", length = 100)
    private String nombre;
    @Column(name="tipo", length = 100)
    private String tipo;
    @Column(name="estado" , length = 1)
    private char estado;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="procesos")
	@JsonIgnore
	private Set<Detalle_PPP> detalle_ppp;
}
