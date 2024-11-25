package com.barbershop.project.repository;

import com.barbershop.project.model.Ordine;
import com.barbershop.project.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Query Jpa effettuabili sull'entity ordine
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    Ordine findById (Long id);
    List<Ordine> findAllByDestinatario_Utente_Id(Long destinatario_utente_id);

    List<Ordine> findAllByDestinatario_Utente(Utente utente);
}
