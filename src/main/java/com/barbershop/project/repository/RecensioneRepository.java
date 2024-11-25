package com.barbershop.project.repository;

import com.barbershop.project.model.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Query Jpa effettuabili sull'entity recensione
public interface RecensioneRepository extends JpaRepository<Recensione, Integer> {
    Recensione findById(Long id);
    List<Recensione> findAllByPrenotazione_Barbiere_Id(Long idBarbiere);
}
