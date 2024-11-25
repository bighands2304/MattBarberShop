package com.barbershop.project.repository;

import com.barbershop.project.model.Prenotazione;
import com.barbershop.project.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//Query Jpa effettuabili sull'entity prenotazione
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
    List<Prenotazione> findAll();
    Prenotazione findById(Long id);
    List<Prenotazione> findByBarbiere(Utente barbiere);
    List<Prenotazione> findAllByBarbiereAndStartTimeIsGreaterThanAndEndTimeIsLessThan (Utente barbiere, LocalDateTime start, LocalDateTime end);
}
