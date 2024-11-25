package pe.edu.upeu.pppmanager.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upeu.pppmanager.entity.Persona;

public interface PersonaService {
	Persona create(Persona a);
	Persona update(Persona a);
	void delete(Long id);
	Optional<Persona> read(Long id);
	List<Persona> readAll();
	void deletePersonasBatch(List<Long> ids);
}
