package com.barbershop.project.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Orario")
@EnableAutoConfiguration
public final class Orario {
    private static Orario INSTANCE;

    public Orario() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.minutiPerTurno = 30;
        this.orarioApertura = LocalTime.parse("09:00", formatter);
        this.orarioChiusura = LocalTime.parse("18:30", formatter);
        this.orarioInizioPausa = LocalTime.parse("12:30", formatter);
        this.orarioFinePausa = LocalTime.parse("14:30", formatter);
        this.giorniOrariValidi = Arrays.asList(false, true, true, true, true, true, true);
    }

    public static Orario getInstance(){
        if (INSTANCE==null){
            INSTANCE= new Orario();
        }
        return INSTANCE;
    }

    public static List<List<LocalTime>> intervalli(LocalTime inizio, LocalTime fine) {
        List<List<LocalTime>> turni = new ArrayList<>();
        LocalTime time = inizio;
        while (time.isBefore(fine)) {
            List<LocalTime> turno = new ArrayList<>();
            turno.add(time);
            time = time.plusMinutes(INSTANCE.minutiPerTurno);
            turno.add(time);
            turni.add(turno);
        }
        return turni;
    }
    public List<List<LocalTime>> getIntervalli(){
        List<List<LocalTime>> intervalli = new ArrayList<>();
        for (List<LocalTime> intervallo : intervalli(getOrarioApertura(), getOrarioInizioPausa())) {
            intervalli.add(intervallo);
        }
        for (List<LocalTime> intervallo : intervalli(getOrarioFinePausa(), getOrarioChiusura())) {
            intervalli.add(intervallo);
        }
        return intervalli;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "minutiPerTurno")
    private Integer minutiPerTurno;

    @Column(name = "orarioApertura")
    private LocalTime orarioApertura;

    @Column(name = "orarioChiusura")
    private LocalTime orarioChiusura;

    @Column(name = "orarioInizioPausa")
    private LocalTime orarioInizioPausa;

    @Column(name = "orarioFinePausa")
    private LocalTime orarioFinePausa;

    @Column(name = "giorniOrariValidi")
    @ElementCollection
    private List<Boolean> giorniOrariValidi = new ArrayList<>(7);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinutiPerTurno() {
        return minutiPerTurno;
    }

    public void setMinutiPerTurno(Integer minutiPerTurno) {
        this.minutiPerTurno = minutiPerTurno;
    }

    public LocalTime getOrarioApertura() {
        return orarioApertura;
    }

    public void setOrarioApertura(LocalTime orarioApertura) {
        this.orarioApertura = orarioApertura;
    }

    public LocalTime getOrarioChiusura() {
        return orarioChiusura;
    }

    public void setOrarioChiusura(LocalTime orarioChiusura) {
        this.orarioChiusura = orarioChiusura;
    }

    public LocalTime getOrarioInizioPausa() {
        return orarioInizioPausa;
    }

    public void setOrarioInizioPausa(LocalTime orarioInizioPausa) {
        this.orarioInizioPausa = orarioInizioPausa;
    }

    public LocalTime getOrarioFinePausa() {
        return orarioFinePausa;
    }

    public void setOrarioFinePausa(LocalTime orarioFinePausa) {
        this.orarioFinePausa = orarioFinePausa;
    }

    public List<Boolean> getGiorniOrariValidi() {
        return giorniOrariValidi;
    }

    public void setGiorniOrariValidi(List<Boolean> giorniOrariValidi) {
        this.giorniOrariValidi = giorniOrariValidi;
    }
}
