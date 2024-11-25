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
	@Table(name = "Linea")
	@CrossOrigin
public class Linea {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id_linea")
		private Long id_linea;
		
	    @ManyToOne
	    @JoinColumn(name = "id_carrera")
	    private Carrera carrera;
		
		@Column(name="nombre", length = 100)
		private String nombre;
		
		@Column(name="estado", length = 1, nullable = false)
		private char estado;

		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="linea")
		@JsonIgnore
		private Set<PPP> ppp;
}
