package pe.edu.upeu.pppmanager.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upeu.pppmanager.entity.Persona;
import pe.edu.upeu.pppmanager.repository.PersonaRepository;
import pe.edu.upeu.pppmanager.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {
	
	@Autowired
	private PersonaRepository repository;
	
	@Override
	public Persona create(Persona a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Persona update(Persona a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public Optional<Persona> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Persona> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public void deletePersonasBatch(List<Long> ids) {
	    repository.deleteAllById(ids); 
	}
	

}
