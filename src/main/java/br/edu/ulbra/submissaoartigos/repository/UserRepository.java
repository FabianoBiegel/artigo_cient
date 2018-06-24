package br.edu.ulbra.submissaoartigos.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ulbra.submissaoartigos.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
