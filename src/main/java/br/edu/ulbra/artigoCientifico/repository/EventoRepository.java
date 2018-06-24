package br.edu.ulbra.artigoCientifico.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ulbra.artigoCientifico.model.Evento;

public interface EventoRepository extends CrudRepository<Evento, Long> {

}
