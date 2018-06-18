package br.edu.ulbra.artigocientifico.repository;

import br.edu.ulbra.artigocientifico.model.Avaliacao;
import br.edu.ulbra.artigocientifico.model.User;
import br.edu.ulbra.artigocientifico.model.Vinho;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvaliacaoRepository extends CrudRepository<Avaliacao, Long> {
	List<Avaliacao> findByUser(User user);
	Avaliacao findByUserAndVinho(User username, Vinho vinho);
}
