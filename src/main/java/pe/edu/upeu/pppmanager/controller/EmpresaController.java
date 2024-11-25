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
import pe.edu.upeu.pppmanager.entity.Empresa;
import pe.edu.upeu.pppmanager.service.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpresaController {
	@Autowired
	private EmpresaService empresaService;
	
	 @GetMapping
	    public ResponseEntity<List<Empresa>> readAll() {
	        try {
	            List<Empresa> empresas = empresaService.readAll();
	            if (empresas.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(empresas, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Empresa> create(@Valid @RequestBody Empresa empresa) {
	        try {
	        	Empresa newEmpresa = empresaService.create(empresa);
	            return new ResponseEntity<>(newEmpresa, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Empresa> getEmpresaId(@PathVariable("id") Long id) {
	        try {
	            Optional<Empresa> empresas = empresaService.read(id);
	            return empresas.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteEmpresa(@PathVariable("id") Long id) {
	        try {
	            empresaService.delete(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Empresa> updateEmpresa(@PathVariable("id") Long id, @Valid @RequestBody Empresa empresas) {
	        Optional<Empresa> existingEmpresa = empresaService.read(id);

	        if (existingEmpresa.isPresent()) {
	            Empresa updatedEmpresa = existingEmpresa.get();
	            
	            updatedEmpresa.setRazon_social(empresas.getRazon_social());
	            updatedEmpresa.setSector(empresas.getSector());
	            updatedEmpresa.setRUC(empresas.getRUC());
	            updatedEmpresa.setDireccion(empresas.getDireccion());
	            updatedEmpresa.setRepresentante_legal(empresas.getRepresentante_legal());
	            updatedEmpresa.setEstado(empresas.getEstado());


	            empresaService.update(updatedEmpresa);
	            return new ResponseEntity<>(updatedEmpresa, HttpStatus.OK);
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
	            empresaService.deleteEmpresasBatch(ids);
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
