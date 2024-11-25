package pe.edu.upeu.pppmanager.service;
import java.util.List;
import java.util.Optional;


import pe.edu.upeu.pppmanager.entity.Sede;


public interface SedeService {
	Sede create(Sede a);
	Sede update(Sede a);
	void delete(Long id);
	Optional<Sede> read(Long id);
	List<Sede> readAll();
	void deleteSedesBatch(List<Long> ids);
}

