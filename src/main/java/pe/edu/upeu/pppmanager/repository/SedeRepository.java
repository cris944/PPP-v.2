package pe.edu.upeu.pppmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upeu.pppmanager.entity.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede,Long>{

}

