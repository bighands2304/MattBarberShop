package com.barbershop.project.email;

import com.barbershop.project.model.*;
import com.barbershop.project.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

@Service
public class EmailSender {

    @Autowired
    EmailService emailService;
    @Autowired
    UtenteRepository utenteRepository;

    @Value("${frontend.path}")
    private String urlFrontend;

    public void emailNuovaPrenotazione(Prenotazione prenotazione) throws MalformedURLException, UnsupportedEncodingException {
        //Email al barbiere
        if(prenotazione.getBarbiere().isNotifiche()) {
            emailService.sendSimpleMail(new EmailDetails(
                    prenotazione.getBarbiere().getMail(),
                    "Hai una nuova prenotazione a nome " + prenotazione.getNome() + ", il giorno " + prenotazione.getStartTime().toLocalDate() + " alle ore " + prenotazione.getStartTime().toLocalTime() +
                            "\nAccedi al tuo profilo per visualizzare tutte le prenotazioni a questo link: " + urlFrontend + "/login",
                    "Nuova prenotazione"));
        }
        //Email agli admin
        for (Utente admin: utenteRepository.findAllByRoleEquals("ROLE_ADMIN")) {
            if (admin.isNotifiche()) {
                emailService.sendSimpleMail(new EmailDetails(
                        admin.getMail(),
                        "Il barbiere " + prenotazione.getBarbiere().getNome() + ", ha ricevuto una nuova prenotazione a nome " + prenotazione.getNome() + ", il giorno " + prenotazione.getStartTime().toLocalDate() + " alle ore " + prenotazione.getStartTime().toLocalTime() +
                                "\nAccedi al tuo profilo per visualizzare tutte le prenotazioni a questo link: " + urlFrontend + "/login",
                        "Nuova prenotazione"));
            }
        }
        //Email al cliente
        emailService.sendSimpleMail(new EmailDetails(
                prenotazione.getMail(),
                "Hai effettuato una nuova prenotazione a nome " + prenotazione.getNome() +", il giorno "+prenotazione.getStartTime().toLocalDate() +" dalle ore " + prenotazione.getStartTime().toLocalTime() + " alle ore: " + prenotazione.getEndTime().toLocalTime() + ". Il prezzo totale è di: €" + new DecimalFormat("0.00").format(prenotazione.getPrezzo()) +
                        "\nVuoi disdire la tua prenotazione? Fallo a questo link: " + urlFrontend + "/cancellaPrenotazione/" + prenotazione.getId() +
                        "\nVuoi modificare la tua prenotazione? Fallo a questo link: " + urlFrontend + "/modificaPrenotazione/" + prenotazione.getId(),
                "Nuova prenotazione"));

        //Email al cliente recensione
        emailService.sendSimpleMail(new EmailDetails(
                prenotazione.getMail(),
                "Com'è andato il tuo appuntamento? Faccelo sapere con una recensione a questo link: " +  urlFrontend + "/recensioni/" + prenotazione.getId(),
                "Facci sapere com'è andata"));
    }

    public void emailNuovoOrdine(Ordine ordine) {
        //Email al cliente
        if (ordine.getDestinatario().getUtente().isNotifiche()) {
            emailService.sendSimpleMail(new EmailDetails(ordine.getDestinatario().getUtente().getMail(),
                    "Grazie per il tuo ordine! Hai acquistato " + String.join(", ", ordine.getProdotti().stream().map(Prodotto::getNome).collect(Collectors.toList())) + " il prezzo totale è di: €" + new DecimalFormat("0.00").format(ordine.getProdotti().stream().mapToDouble(Prodotto::getPrezzo).sum()),
                    "Nuovo ordine"));
        }

        //Email agli admin
        for (Utente admin: utenteRepository.findAllByRoleEquals("ROLE_ADMIN")) {
            if (admin.isNotifiche()) {
                emailService.sendSimpleMail(new EmailDetails(
                        admin.getMail(),
                        "Hai ricevuto un nuovo ordine! L'utente " + ordine.getDestinatario().getUtente().getNome() + ", ha acquistato i seguenti prodotti: " + ordine.getProdotti().stream().map(Prodotto::getNome).collect(Collectors.toList()) +
                                "\nAccedi al tuo profilo per controllare tutti gli ordini al seguente link: " + urlFrontend +"/login",
                        "Nuovo ordine"));
            }
        }


    }

    public void emailNuovaRecensione(Recensione recensione){
        //Email al barbiere
        if (recensione.getPrenotazione().getBarbiere().isNotifiche()){
            emailService.sendSimpleMail(new EmailDetails(
                    recensione.getPrenotazione().getBarbiere().getMail(),
                    "Hai una nuova recensione! La tua valutazione è di " + recensione.getValutazione() + " stelle." + (recensione.getDescrizione().isEmpty() ? "" : " Il cliente ha lasciato il seguente commento: \"" + recensione.getDescrizione()) + "\"",
                    "Nuova recensione"));
        }

        //Email agli admin
        for (Utente admin: utenteRepository.findAllByRoleEquals("ROLE_ADMIN")) {
            if (admin.isNotifiche()) {
                emailService.sendSimpleMail(new EmailDetails(
                        admin.getMail(),
                        "Il barbiere " + recensione.getPrenotazione().getBarbiere().getNome() + " ha ricevuto una nuova recensione! La sua valutazione è di" + recensione.getValutazione() + " stelle." + (recensione.getDescrizione().isEmpty() ? "" : " Il cliente ha lasciato il seguente commento: \"" + recensione.getDescrizione()) +"\"",
                        "Nuova recensione"));
            }
        }

    }

    public void emailPrenotazioneEliminata(Prenotazione prenotazione){
        //Email al barbiere
        if(prenotazione.getBarbiere().isNotifiche()) {
            emailService.sendSimpleMail(new EmailDetails(
                    prenotazione.getBarbiere().getMail(),
                    "La prenotazione a nome " + prenotazione.getNome() + " del giorno " + prenotazione.getStartTime().toLocalDate() + " alle ore " + prenotazione.getStartTime().toLocalTime() +", è stata disdetta.",
                    "Prenotazione disdetta"));
        }
        //Email agli admin
        for (Utente admin: utenteRepository.findAllByRoleEquals("ROLE_ADMIN")) {
            if (admin.isNotifiche()) {
                emailService.sendSimpleMail(new EmailDetails(
                        admin.getMail(),
                        "La prenotazione del barbiere " + prenotazione.getBarbiere().getNome() + ", a nome " + prenotazione.getNome() + ", il giorno " + prenotazione.getStartTime().toLocalDate() + " alle ore " + prenotazione.getStartTime().toLocalTime()+", è stata disdetta.",
                        "Prenotazione disdetta"));
            }
        }
        //Email al cliente
        emailService.sendSimpleMail(new EmailDetails(
                prenotazione.getMail(),
                "La tua prenotazione a nome " + prenotazione.getNome() +", il giorno "+prenotazione.getStartTime().toLocalDate() +" alle ore " + prenotazione.getStartTime().toLocalTime() + "è stata disdetta, a presto!",
                "Prenotazione disdetta"));
    }
}
