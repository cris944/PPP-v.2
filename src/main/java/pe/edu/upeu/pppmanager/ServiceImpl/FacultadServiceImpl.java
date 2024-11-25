package pe.edu.upeu.pppmanager.ServiceImpl;

import java.util.List;
import java.util.Optional;

import pe.edu.upeu.pppmanager.entity.Facultad;
import pe.edu.upeu.pppmanager.repository.FacultadRepository;
import pe.edu.upeu.pppmanager.service.FacultadService;

public class FacultadServiceImpl implements FacultadService{
	
	private FacultadRepository repository;

	@Override
	public Facultad create(Facultad a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Facultad update(Facultad a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);	
	}

	@Override
	public Optional<Facultad> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Facultad> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public void deleteFacultadesBatch(List<Long> ids) {
		// TODO Auto-generated method stub
		repository.deleteAllById(ids); 
	}

	

}
