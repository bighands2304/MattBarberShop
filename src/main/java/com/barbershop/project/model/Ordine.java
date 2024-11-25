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
@Table(name = "Ordine")
@EnableAutoConfiguration
@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_mittente")
    private Indirizzo mittente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_destinatario")
    private Indirizzo destinatario;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name="Ordine_prodotto"
            ,joinColumns={@JoinColumn(name="id_ordine")}
            ,inverseJoinColumns={@JoinColumn(name="id_prodotto")}
    )
    private List<Prodotto> prodotti = new ArrayList<>();

    public Ordine() {}

    public Long getId() { return id; }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public Indirizzo getMittente() {
        return mittente;
    }

    public void setMittente(Indirizzo mittente) {
        this.mittente = mittente;
    }

    public Indirizzo getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Indirizzo destinatario) {
        this.destinatario = destinatario;
    }
}
