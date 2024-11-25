package com.barbershop.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="Indirizzo")
@EnableAutoConfiguration
@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "via")
    private String via;
    @Column(name = "numero_civico")
    private String numeroCivico;
    @Column(name = "cap")
    private String cap;
    @Column(name = "citta")
    private String citta;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "mittente")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Ordine> ordiniMittente = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "destinatario")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Ordine> ordiniDestinatario = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Ordine> getOrdiniMittente() {
        return ordiniMittente;
    }
    public List<Ordine> getOrdiniDestinatario() {
        return ordiniDestinatario;
    }
}
