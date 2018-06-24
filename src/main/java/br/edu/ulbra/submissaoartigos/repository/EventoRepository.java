package br.edu.ulbra.submissaoartigos.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ulbra.submissaoartigos.model.Evento;

public interface EventoRepository extends CrudRepository<Evento, Long> {

}
