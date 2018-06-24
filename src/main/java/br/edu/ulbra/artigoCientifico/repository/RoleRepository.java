package br.edu.ulbra.artigoCientifico.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ulbra.artigoCientifico.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
