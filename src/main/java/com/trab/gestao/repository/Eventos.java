package com.trab.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trab.gestao.model.Evento;

  public interface Eventos extends JpaRepository<Eventos, Long> {


}