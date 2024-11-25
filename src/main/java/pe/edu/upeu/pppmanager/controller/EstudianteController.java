package pe.edu.upeu.pppmanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.edu.upeu.pppmanager.entity.Estudiante;
import pe.edu.upeu.pppmanager.service.EstudianteService;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "http://localhost:4200")
public class EstudianteController {
	@Autowired
	private EstudianteService estudianteService;
	
	 @GetMapping
	    public ResponseEntity<List<Estudiante>> readAll() {
	        try {
	            List<Estudiante> estudiantes = estudianteService.readAll();
	            if (estudiantes.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(estudiantes, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Estudiante> create(@Valid @RequestBody Estudiante estudiante) {
	        try {
	        	Estudiante newEstudiante = estudianteService.create(estudiante);
	            return new ResponseEntity<>(newEstudiante, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Estudiante> getEstudianteId(@PathVariable("id") Long id) {
	        try {
	            Optional<Estudiante> estudiantes = estudianteService.read(id);
	            return estudiantes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteEstudiante(@PathVariable("id") Long id) {
	        try {
	            estudianteService.delete(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable("id") Long id, @Valid @RequestBody Estudiante estudiantes) {
	        Optional<Estudiante> existingEstudiante = estudianteService.read(id);

	        if (existingEstudiante.isPresent()) {
	            Estudiante updatedEstudiante = existingEstudiante.get();
	            
	            updatedEstudiante.setPersona(estudiantes.getPersona());
	            updatedEstudiante.setCodigo(estudiantes.getCodigo());


	            estudianteService.update(updatedEstudiante);
	            return new ResponseEntity<>(updatedEstudiante, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

}
