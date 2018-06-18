package br.edu.ulbra.artigocientifico.repository;

import br.edu.ulbra.artigocientifico.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
