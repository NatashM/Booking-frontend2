package com.example.eksamens24timers.repository;

import com.example.eksamens24timers.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    Optional<Pizza> findByTitle(String title);

}
