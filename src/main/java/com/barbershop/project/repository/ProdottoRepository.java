package com.barbershop.project.repository;

import com.barbershop.project.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

//Query Jpa effettuabili sull'entity prodotto
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    Prodotto findById(Long id);
}
