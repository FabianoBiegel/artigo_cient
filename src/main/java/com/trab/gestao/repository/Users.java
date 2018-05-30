package com.trab.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trab.gestao.model.User;

  public interface Users extends JpaRepository<Users, Long> {


}