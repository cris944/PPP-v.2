package pe.edu.upeu.pppmanager.service;

import java.util.List;
import java.util.Optional;


import pe.edu.upeu.pppmanager.entity.Empresa;


public interface EmpresaService {
	Empresa create(Empresa a);
	Empresa update(Empresa a);
	void delete(Long id);
	Optional<Empresa> read(Long id);
	List<Empresa> readAll();
	void deleteEmpresasBatch(List<Long> ids);
}
