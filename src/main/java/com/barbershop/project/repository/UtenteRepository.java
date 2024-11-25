package com.barbershop.project.repository;

import com.barbershop.project.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

//Query Jpa effettuabili sull'entity utente
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Utente findById(Long id);
    Utente findByMail(String mail);
    Set<Utente> findAllByRoleEquals(String role);
    Utente findByIdAndRoleEquals(Long id, String role);
}
