package com.barbershop.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Utente")
@EnableAutoConfiguration
@JsonIgnoreProperties(value= {"password", "handler","hibernateLazyInitializer","FieldHandler"})

public class Utente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name="id", nullable = false, unique = true)
    private Long id;
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;

    //Campi solo per i barbieri
    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "cellulare")
    private String cellulare;
    @Lob
    private String foto;
    @Column(name = "notifiche")
    private Boolean notifiche;
    @Column(name = "ranking")
    private static Boolean ranking;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "barbiere")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "utente")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Indirizzo> indirizzo = new ArrayList<>();

    public Utente() {
        notifiche=true;
        if (ranking==null){
            ranking=true;
        }
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public List<Indirizzo> getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(List<Indirizzo> indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void addIndirizzo(Indirizzo indirizzo){
        this.indirizzo.add(indirizzo);
    }

    public boolean isNotifiche() {
        return notifiche;
    }

    public void toggleNotifiche() {
        notifiche = !notifiche;
    }

    public static boolean isRanking(){
        return ranking;
    }

    public static void toggleRanking(){
        if (ranking==null){
            ranking=true;
        }else{
            ranking=!ranking;
        }
    }


}
