package br.edu.ulbra.submissaoartigos.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ulbra.submissaoartigos.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
