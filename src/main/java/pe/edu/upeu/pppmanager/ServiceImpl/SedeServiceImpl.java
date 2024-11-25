package pe.edu.upeu.pppmanager.ServiceImpl;

import java.util.List;
import java.util.Optional;

import pe.edu.upeu.pppmanager.entity.Sede;
import pe.edu.upeu.pppmanager.repository.PersonaRepository;
import pe.edu.upeu.pppmanager.repository.SedeRepository;
import pe.edu.upeu.pppmanager.service.SedeService;

public class SedeServiceImpl implements SedeService {

	private SedeRepository repository;
	
	@Override
	public Sede create(Sede a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Sede update(Sede a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);	
	}

	@Override
	public Optional<Sede> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Sede> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public void deleteSedesBatch(List<Long> ids) {
		// TODO Auto-generated method stub
		repository.deleteAllById(ids); 
	}

}
