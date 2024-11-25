	package pe.edu.upeu.pppmanager.controller;
	
	import java.util.Collections;
import java.util.List;
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	import pe.edu.upeu.pppmanager.entity.Persona;
	import pe.edu.upeu.pppmanager.service.PersonaService;
	
	@RestController
	@RequestMapping("/api/personas")
	@CrossOrigin(origins = "http://localhost:4200")
	public class PersonaController {
		
		@Autowired
		private PersonaService personaService;
		
		 @GetMapping
		    public ResponseEntity<List<Persona>> readAll() {
		        try {
		            List<Persona> personas = personaService.readAll();
		            if (personas.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		            }
		            return new ResponseEntity<>(personas, HttpStatus.OK);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
	
		    @PostMapping
		    public ResponseEntity<Persona> create(@Valid @RequestBody Persona personas) {
		        try {
		        	Persona newPersona = personaService.create(personas);
		            return new ResponseEntity<>(newPersona, HttpStatus.CREATED);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
	
		    @GetMapping("/{id}")
		    public ResponseEntity<Persona> getPersonaId(@PathVariable("id") Long id) {
		        try {
		            Optional<Persona> personas = personaService.read(id);
		            return personas.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
		                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
	
		    @DeleteMapping("/{id}")
		    public ResponseEntity<Void> deletePersona(@PathVariable("id") Long id) {
		        try {
		            personaService.delete(id);
		            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		        } catch (Exception e) {
		            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
	
		    @PutMapping("/{id}")
		    public ResponseEntity<Persona> updateAlumno(@PathVariable("id") Long id, @Valid @RequestBody Persona personas) {
		        Optional<Persona> existingPersona = personaService.read(id);
	
		        if (existingPersona.isPresent()) {
		            Persona updatedPersona = existingPersona.get();
		            
		            updatedPersona.setApellido(personas.getApellido());
		            updatedPersona.setNombre(personas.getNombre());
		            updatedPersona.setDni(personas.getDni());
		            updatedPersona.setCorreo(personas.getCorreo());
		            updatedPersona.setTelefono(personas.getTelefono());
		            updatedPersona.setEstado(personas.getEstado());
	
		            personaService.update(updatedPersona);
		            return new ResponseEntity<>(updatedPersona, HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
		    
		    @DeleteMapping("/batch")
		    public ResponseEntity<?> deletePersonasBatch(@RequestBody List<Long> ids) {
		        System.out.println("IDs recibidos para eliminar: " + ids); // Log de depuración

		        HttpHeaders headers = new HttpHeaders();
		        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		        try {
		            personaService.deletePersonasBatch(ids);
		            return ResponseEntity.ok()
		                    .headers(headers)
		                    .body(Collections.singletonMap("message", "Personas eliminadas exitosamente"));
		        } catch (Exception e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .headers(headers)
		                    .body(Collections.singletonMap("error", e.getMessage()));
		        }
		    }

	}
