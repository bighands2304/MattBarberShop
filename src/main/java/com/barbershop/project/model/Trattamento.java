package com.barbershop.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Trattamento")
@EnableAutoConfiguration
public class Trattamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "prezzo")
    private Double prezzo;
    @Column(name = "durata")
    private Integer durata;

    @ManyToMany(mappedBy="trattamenti", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Prenotazione> prenotazioni = new HashSet<Prenotazione>();

    public Long getId() { return id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getDurata() { return durata; }

    public void setDurata(Integer durata) {
        this.durata = durata;
    }

    public Set<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void addPrenotazione(Prenotazione prenotazione) { this.prenotazioni.add(prenotazione); }
}
