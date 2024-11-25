package pe.edu.upeu.pppmanager.entity;

import java.sql.Date;

import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="detalle_documento")
@CrossOrigin
public class Detalle_Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle_documento")
    private Long id_detalle_documento;

    @Column(name="url")
    private String url;

    @Column(name="fecha")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_detalle_ppp")
    private Detalle_PPP detalle_ppp;

    @Column(name = "estado",length = 1) 
    private char estado;
}
