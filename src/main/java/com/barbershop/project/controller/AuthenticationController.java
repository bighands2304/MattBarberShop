package com.barbershop.project.controller;

import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.UtenteRepository;
import com.barbershop.project.security.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@CrossOrigin(origins = "${frontend.path}")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try{
            //Crea un autenticazione tramite i valori username e password passati nel body
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new AccessDeniedException("Credenziali errate");
        }
        //Cerco l'esistenza dell'utente nel db
        final UtentePrincipal userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        //Genero la stringa jwt relativa a quell'utente
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        //Tento l'autenticazione con il token jwt precedente
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwt);

        //Invio la risposta con l'autenticazione e i dettagli dell'utente
        Map<String,Object> response = new HashMap<>();
        response.put("jwt", authenticationResponse.getJwt());
        response.put("user", utenteRepository.findById(userDetails.getId()));
        return ResponseEntity.ok(response);
    }
}
