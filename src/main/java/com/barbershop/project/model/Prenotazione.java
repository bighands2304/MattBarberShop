package com.barbershop.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Prenotazione")
@EnableAutoConfiguration
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "mail")
    private String mail;
    @Column(name = "start_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;
    @Column(name = "end_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;
    @Column(name = "prezzo")
    private Double prezzo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_barbiere")
    private Utente barbiere;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_recensione", referencedColumnName = "id")
    @JsonIgnore
    private Recensione recensione;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name="Prenotazione_trattamento"
            ,joinColumns={@JoinColumn(name="id_prenotazione")}
            ,inverseJoinColumns={@JoinColumn(name="id_trattamento")}
    )
    private Set<Trattamento> trattamenti = new HashSet<Trattamento>();

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Utente getBarbiere() {
        return barbiere;
    }

    public void setBarbiere(Utente barbiere) {
        this.barbiere = barbiere;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }

    public Set<Trattamento> getTrattamenti() {
        return trattamenti;
    }

    public void addTrattamento(Trattamento trattamento) {
        this.trattamenti.add(trattamento);
    }

    public void resetTrattamenti(){ trattamenti=new HashSet<>();}
}
