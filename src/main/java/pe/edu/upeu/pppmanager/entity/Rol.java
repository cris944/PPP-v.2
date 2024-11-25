package pe.edu.upeu.pppmanager.entity;

import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "rol")
@CrossOrigin
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol")
    private Long id;
    @Column(name="nombre",length = 100)
    private String name;
	@Column(name="estado", length = 1)
	private char estado;
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Rol_Privilegio", 
            joinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"), 
            inverseJoinColumns = @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio")
           )
          private Set<Privilegio> privilegio;
}