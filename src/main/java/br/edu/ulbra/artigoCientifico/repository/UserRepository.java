package br.edu.ulbra.artigoCientifico.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ulbra.artigoCientifico.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
