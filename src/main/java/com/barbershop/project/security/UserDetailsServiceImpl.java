package com.barbershop.project.security;

import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UtenteRepository utenteRepository;

    @Override
    public UtentePrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByMail(username);
        if (utente == null){
            throw new UsernameNotFoundException("Utente non trovato");
        }

        return new UtentePrincipal(utente);
    }
}
