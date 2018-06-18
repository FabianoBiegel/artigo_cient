package br.edu.ulbra.artigocientifico.repository;

import br.edu.ulbra.artigocientifico.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
