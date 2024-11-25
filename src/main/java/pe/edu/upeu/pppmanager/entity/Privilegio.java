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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="privilegio")
@CrossOrigin
public class Privilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_privilegio")
    private Long id_privilegio;
    @Column(name="nombre", length = 100)
    private String nombre;
    @Column(name="img_perfil", length = 100)
    private String img_perfil;
    @Column(name="estado" , length = 1,nullable = false)
    private char estado;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Usuario_Privilegio", 
            joinColumns = @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio"), 
            inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
           )
          private Set<Usuario> usuario;

    
    
}
