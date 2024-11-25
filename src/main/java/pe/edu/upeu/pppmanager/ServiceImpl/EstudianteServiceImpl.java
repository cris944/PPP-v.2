package pe.edu.upeu.pppmanager.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upeu.pppmanager.entity.Estudiante;
import pe.edu.upeu.pppmanager.repository.EstudianteRepository;
import pe.edu.upeu.pppmanager.service.EstudianteService;

@Service
public class EstudianteServiceImpl implements EstudianteService {

	@Autowired
	private EstudianteRepository repository;
	
	@Override
	public Estudiante create(Estudiante a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Estudiante update(Estudiante a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public Optional<Estudiante> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Estudiante> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
