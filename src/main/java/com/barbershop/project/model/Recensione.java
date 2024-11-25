package com.barbershop.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Entity
@Table(name = "Recensione")
@EnableAutoConfiguration
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "valutazione", nullable = false)
    private Float valutazione;
    @Column(name = "descrizione")
    private String descrizione;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_prenotazione", referencedColumnName = "id")
    @JsonIgnore
    private Prenotazione prenotazione;

    public Long getId() { return id; }

    public Float getValutazione() {
        return valutazione;
    }

    public void setValutazione(Float valutazione) {
        this.valutazione = valutazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
}
