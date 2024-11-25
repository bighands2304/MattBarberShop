package com.barbershop.project.repository;

import com.barbershop.project.model.Trattamento;
import org.springframework.data.jpa.repository.JpaRepository;

//Query Jpa effettuabili sull'entity trattamento
public interface TrattamentoRepository extends JpaRepository<Trattamento, Integer> {
    Trattamento findById(Long id);
}
