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
@Table(name="empresa")
@CrossOrigin
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_empresa")
    private Long id;
    
    @Column(name="razon_social", length = 100)
    private String razon_social;
    
    @Column(name="direccion", length = 100)
    private String direccion;
    
    @Column(name="sector", length = 100)
    private String sector;
    
    @Column(name="RUC", length = 11)
    private String RUC;
    
    @Column(name="representante_legal", length = 100)
    private String representante_legal;
    
    @Column(name="estado" , length = 1, nullable = false)
    private char estado;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="empresa")
	@JsonIgnore
	private Set<PPP> ppp;
}