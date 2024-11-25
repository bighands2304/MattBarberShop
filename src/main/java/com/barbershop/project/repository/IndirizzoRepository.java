package com.barbershop.project.repository;

import com.barbershop.project.model.Indirizzo;
import com.barbershop.project.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

//Query Jpa effettuabili sull'entity indirizzo
public interface IndirizzoRepository extends JpaRepository<Indirizzo, Integer> {
    Indirizzo findIndirizzoById(Long id);
    ArrayList<Indirizzo> findAllByUtente (Utente utente);
    Indirizzo findIndirizzoByUtente(Utente utente);

}
