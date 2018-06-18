package br.edu.ulbra.artigocientifico.repository;

import br.edu.ulbra.artigocientifico.model.Evento;
import br.edu.ulbra.artigocientifico.model.User;
import br.edu.ulbra.artigocientifico.model.Sub;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventoRepository extends CrudRepository<Evento, Long> {
	List<Evento> findByUser(User user);
	Evento findByUserAndVinho(User username, Sub sub);
}
