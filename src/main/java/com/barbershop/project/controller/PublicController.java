package com.barbershop.project.controller;

import com.barbershop.project.email.EmailSender;
import com.barbershop.project.model.*;
import com.barbershop.project.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

@Transactional
@RestController
@CrossOrigin(origins = "${frontend.path}")
@RequestMapping("/public")
public class PublicController {
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Autowired
    RecensioneRepository recensioneRepository;
    @Autowired
    TrattamentoRepository trattamentoRepository;
    @Autowired
    ProdottoRepository prodottoRepository;
    @Autowired
    IndirizzoRepository indirizzoRepository;
    @Autowired
    OrdineRepository ordineRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailSender emailSender;

    /*
     *  METODI POST
     */

    /*
    Metodo che crea nuovi utenti user

        ESEMPIO DI JSON
        {
            "mail":"mariorossi@mail.com",       OBBLIGATORIO
            "password":"password",              OBBLIGATORIO
            "nome":"Mario",
            "cognome":"Rossi",
            "cellulare":"1234567890",
            "foto":"C:\\Users\\utente\\Pictures\\pic.jpg"
        }
    */
    @PostMapping("/nuovoUtente")
    public ResponseEntity<?> nuovoUtente(@RequestBody Map<String, String> payload) {
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<String>();

        //Check se l'utente esiste
        if (utenteRepository.findByMail(payload.get("mail")) != null) {
            messages.add("Mail già utilizzata.");
        }
        //Check se tutti i valori obbligatori sono stati inviati
        else if(payload.containsKey("mail") && payload.containsKey("password")){
            Utente utente = new Utente();

            //Verifica campo mail non vuoto e set
            if (!payload.get("mail").equals("")) {
                try {
                    utente.setMail(payload.get("mail"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            } else {
                messages.add("Mail non valida.");
            }

            //Verifica campo password non vuoto e set
            if (!payload.get("password").equals("")) {
                try {
                    //Applico la crittografia alla password
                    utente.setPassword(passwordEncoder.encode(payload.get("password")));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            } else {
                messages.add("Password non valida.");
            }

            //Imposto ruolo come ROLE_USER
            try {
                utente.setRole("ROLE_USER");
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Campi superflui alla creazione dell'utente
            if (payload.containsKey("nome")) {
                try {
                    utente.setNome(payload.get("nome"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            if (payload.containsKey("cognome")) {
                try{
                    utente.setCognome(payload.get("cognome"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }
            if (payload.containsKey("cellulare")) {
                try{
                    utente.setCellulare(payload.get("cellulare"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }
            if (payload.containsKey("foto")) {
                try {
                    utente.setFoto(payload.get("foto"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            try {
                //Salvataggio utente e risposta
                utenteRepository.save(utente);
                return ResponseEntity.ok(utente);
            }catch (Exception e){
                messages.add(e.toString());
            }
        } else {
            messages.add("Campi insufficienti alla creazione di un utente.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
        ESEMPIO DI JSON
        {
            "nome":"Giorgio Carletti",              OBBLIGATORIO
            "mail":"giorgio.carletti@gmail.com",    OBBLIGATORIO
            "idBarbiere":"1",                       OBBLIGATORIO
            "startTime":"25/08/2021 14:30",         OBBLIGATORIO
            "trattamenti":"1,2,3"
        }
     */
    @PostMapping("/nuovaPrenotazione")
    public ResponseEntity<?> nuovaPrenotazione(@RequestBody Map<String, String> payload) {
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<String>();

        //Verifica che tutti i campi obbligatori siano presenti
        if(payload.containsKey("nome") && payload.containsKey("mail") && payload.containsKey("idBarbiere") && payload.containsKey("startTime")){
            Prenotazione prenotazione = new Prenotazione();

            //Verifica validità del nome
            if(!payload.get("nome").equals("")) {
                prenotazione.setNome((String) payload.get("nome"));
            } else {
                messages.add("Nome non valido.");
            }

            //Verifica validità della mail
            if(!payload.get("mail").equals("")){
                prenotazione.setMail((String) payload.get("mail"));
            } else {
                messages.add("Mail non valida.");
            }

            //Verifica validità del barbiere
            if(utenteRepository.findByIdAndRoleEquals(Long.parseLong(payload.get("idBarbiere")), "ROLE_BARBIERE") != null) {
                prenotazione.setBarbiere(utenteRepository.findById(Long.parseLong(payload.get("idBarbiere"))));
            } else {
                messages.add("Barbiere non valido.");
            }

            //Verifica validità della data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            try {
                LocalDateTime start = LocalDateTime.parse(payload.get("startTime"), formatter);
                if(start.isAfter(LocalDateTime.now())){
                    prenotazione.setStartTime(start);
                } else {
                    messages.add("Orario non valido.");
                }
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Campi superflui alla creazione della prenotazione
            if(payload.containsKey("trattamenti") && payload.get("trattamenti")!="") {
                for (String idTrattamento : new ArrayList<String>(Arrays.asList(payload.get("trattamenti").split(",")))) {
                    if (trattamentoRepository.findById(Long.parseLong(idTrattamento)) != null) {
                        //Aggiunta dei trattamenti alla prenotazione
                        try {
                            prenotazione.addTrattamento(trattamentoRepository.findById(Long.parseLong(idTrattamento)));
                        }catch (Exception e){
                            messages.add(e.toString());
                        }
                    } else {
                        messages.add("Il trattamento con id '" + idTrattamento + "' non è valido.");
                    }
                }
            }

            //Calcolo durata e prezzo prenotazione tramite trattamenti
            Double prezzo = 0.0;
            LocalDateTime durataTaglio = prenotazione.getStartTime();
            for (Trattamento trattamento : prenotazione.getTrattamenti()) {
                durataTaglio=durataTaglio.plusMinutes(trattamento.getDurata());
                prezzo+=trattamento.getPrezzo();
            }
            try {
                prenotazione.setEndTime(durataTaglio);
                prenotazione.setPrezzo(prezzo);
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Salvataggio prenotazione e risposta
             try {
                 prenotazioneRepository.save(prenotazione);
                 //Invio email automatiche
                 emailSender.emailNuovaPrenotazione(prenotazione);

                return ResponseEntity.ok(prenotazione);
            } catch (Exception e){
                messages.add(e.toString());
            }
        } else {
            messages.add("Campi insufficienti alla creazione di una prenotazione.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che crea una nuova recensione per una prenotazione
        ESEMPIO DI JSON
        {
            "idPrenotazione":"1",                   OBBLIGATORIO
            "valutazione":"3.5",                    OBBLIGATORIO
            "descrizione":"Nulla di che"
        }
 */
    @PostMapping("/recensionePrenotazione")
    public ResponseEntity<?> recensionePrenotazione(@RequestBody Map<String,String> payload){
        List<String> messages = new ArrayList<String>();

        if(payload.containsKey("idPrenotazione") && payload.containsKey("valutazione")){
            if(prenotazioneRepository.findById(Long.parseLong(payload.get("idPrenotazione"))) != null) {
                Prenotazione prenotazione = prenotazioneRepository.findById(Long.parseLong(payload.get("idPrenotazione")));

                if (prenotazione.getRecensione()==null) {
                    Recensione recensione = new Recensione();
                    try {
                        recensione.setValutazione(Float.parseFloat(payload.get("valutazione")));
                    } catch (Exception e) {
                        messages.add("Valore valutazione non valido");
                    }
                    if (payload.containsKey("descrizione")) {
                        recensione.setDescrizione(payload.get("descrizione"));
                    }

                    try {
                        recensione.setPrenotazione(prenotazione);
                        prenotazione.setRecensione(recensione);

                        recensioneRepository.save(recensione);
                        prenotazioneRepository.save(prenotazione);

                        //Mail automatiche
                        emailSender.emailNuovaRecensione(recensione);

                        return ResponseEntity.ok(recensione);
                    } catch (Exception e) {
                        messages.add(e.toString());
                    }
                }else {
                    messages.add("Recensione già effettuata");
                }
            } else {
                messages.add("Prenotazione non esistente");
            }
        } else {
            messages.add("Campi insufficienti alla creazione della recensione");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
     *  METODI GET
     */

    /*
    Metodo che restituisce tutti gli utenti barbieri
     */
    @GetMapping("/getBarbieri")
    public ResponseEntity<?> getBarbieri() {
        List<String> messages = new ArrayList<>();
        if (Utente.isRanking()) { //check se l'opzione di ranking è attiva
            Map<Utente, Float> medie = new HashMap<>(); //mappa per associare un utente alla sua media
            for (Utente barbiere : utenteRepository.findAllByRoleEquals("ROLE_BARBIERE")) {
                List<Recensione> recensioni = recensioneRepository.findAllByPrenotazione_Barbiere_Id(barbiere.getId());
                Float somma = 0F;
                for (Recensione recensione : recensioni) {
                    somma += recensione.getValutazione();
                }
                medie.put(barbiere, somma/(recensioni.size()>0 ? recensioni.size() : 1 )); //check se c'è almeno una recensione per evitare la divisione con 0
            }

            //ordino i barbieri in base alle medie
            LinkedHashMap<Utente, Float> barbieriRanked = new LinkedHashMap<>();

            medie.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> barbieriRanked.put(x.getKey(), x.getValue()));

            return ResponseEntity.ok(barbieriRanked.keySet());

        } else {
            return ResponseEntity.ok(utenteRepository.findAllByRoleEquals("ROLE_BARBIERE"));
        }
    }

    /*
    Metodo che restituisce un utente barbiere tramite id
     */
    @GetMapping("/getBarbiere/{idBarbiere}")
    public ResponseEntity<?> getBarbiere(@PathVariable("idBarbiere") Long idBarbiere) {
        return ResponseEntity.ok(utenteRepository.findByIdAndRoleEquals(idBarbiere, "ROLE_BARBIERE"));
    }

    /*
    Metodo che restituisce tutte le prenotazioni
     */
    @GetMapping("/getPrenotazioni")
    public ResponseEntity<?> getPrenotazioni() {
        return ResponseEntity.ok(prenotazioneRepository.findAll());
    }

    /*
    Metodo che restituisce la prenotazioni relativa ad un id
    */
    @GetMapping("/getPrenotazione/{idPrenotazione}")
    public ResponseEntity<?> getPrenotazione(@PathVariable("idPrenotazione") Long idPrenotazione) {
        return ResponseEntity.ok(prenotazioneRepository.findById(idPrenotazione));
    }

    /*
    Metodo che restituisce le prenotazioni relative ad un barbiere tramite id barbiere
     */
    @GetMapping("/getPrenotazioniBarbiere/{idBarbiere}")
    public ResponseEntity<?> getPrenotazioniBarbiere(@PathVariable("idBarbiere") Long idBarbiere) {
        return ResponseEntity.ok(prenotazioneRepository.findByBarbiere(utenteRepository.findById(idBarbiere)));
    }

    /*
    Metodo che restituisce le prenotazioni relative ad un giorno ed un barbiere tramite data e id barbiere
     */
    @GetMapping("/getPrenotazioniBarbierePerGiorno/{idBarbiere}/{giorno}")
    public ResponseEntity<?> getPrenotazioniBarbierePerGiorno(@PathVariable("idBarbiere") Long idBarbiere, @PathVariable("giorno") String giorno){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.parse(giorno, formatter);
        return ResponseEntity.ok(prenotazioneRepository.findAllByBarbiereAndStartTimeIsGreaterThanAndEndTimeIsLessThan(utenteRepository.findById(idBarbiere), data.atStartOfDay(), data.atStartOfDay().plusDays(1)));
    }

    /*
    Metodo che restituisce le fasce orarie disponibili alla prenotazione di un determinato barbiere e giorno tramite data e id barbiere
     */
    @GetMapping("/getPrenotazioniLibereBarbierePerGiorno/{idBarbiere}/{giorno}")
    public ResponseEntity<?> getPrenotazioniLibereBarbierePerGiorno(@PathVariable("idBarbiere") Long idBarbiere, @PathVariable("giorno") String giorno){
        List<String> messages = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.parse(giorno, formatter);
        Orario orario = Orario.getInstance();

        if (orario.getGiorniOrariValidi().get(data.getDayOfWeek().getValue()-1)) {

            List<List<LocalTime>> orari = orario.getIntervalli();

            List<Prenotazione> prenotazioni = prenotazioneRepository.findAllByBarbiereAndStartTimeIsGreaterThanAndEndTimeIsLessThan(utenteRepository.findById(idBarbiere), data.atStartOfDay(), data.atStartOfDay().plusDays(1));

            Predicate<List<LocalTime>> filter = turno -> (data.isEqual(LocalDate.now()) && turno.get(0).isBefore(LocalDateTime.now().toLocalTime()));
            for (Prenotazione prenotazione : prenotazioni) {
                 filter= turno ->
                        ((prenotazione.getStartTime().toLocalTime().equals(turno.get(0))) ||                                                                            //l'inizio del turno coincide con l'inizio della prenotazione
                        (prenotazione.getStartTime().toLocalTime().isAfter(turno.get(0)) && prenotazione.getStartTime().toLocalTime().isBefore(turno.get(1))) ||        //l'inizio della prenotazione è compresa nel turno
                        (prenotazione.getEndTime().toLocalTime().isAfter(turno.get(0)) && prenotazione.getEndTime().toLocalTime().isBefore(turno.get(1))) ||            //la fine della prenotazione è compresa nel turno
                        (turno.get(0).isBefore(prenotazione.getEndTime().toLocalTime()) && turno.get(0).isAfter(prenotazione.getStartTime().toLocalTime())) ||          //se l'inizio del turno è compreso nella prenotazione
                        (turno.get(1).isBefore(prenotazione.getEndTime().toLocalTime()) && turno.get(1).isAfter(prenotazione.getStartTime().toLocalTime())) ||          //se la fine del turno è compresa nella prenotazione
                        (data.isEqual(LocalDate.now()) && turno.get(0).isBefore(LocalDateTime.now().toLocalTime())));                                                   //l'inizio del turno è precedente ad adesso
            }
            orari.removeIf(filter);
            return ResponseEntity.ok(orari);
        }else {
            messages.add("Siamo chiusi");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che restituisce tutti i trattamenti
     */
    @GetMapping("/getTrattamenti")
    public ResponseEntity<?> getTrattamenti() {
        return ResponseEntity.ok(trattamentoRepository.findAll());
    }

    /*
    Metodo che restituisce tutte le recensioni
     */
    @GetMapping("/getRecensioni")
    public ResponseEntity<?> getRecensioni() {
        return ResponseEntity.ok(recensioneRepository.findAll());
    }

    /*
    Metodo che restituisce le ultime tre recensioni aggiunte
     */
    @GetMapping("/getLastThreeRecensioni")
    public ResponseEntity<?> getLastThreeRecensioni(){
        Pageable limit = PageRequest.of(0,3, Sort.by("id").descending());
        return ResponseEntity.ok(recensioneRepository.findAll(limit).getContent());
    }

    /*
    Metodo che restituisce le recensioni relative ad un barbiere tramite id barbiere

        ESEMPIO DI JSON
        {
            "idBarbiere":"1"                    OBBLIGATORIO
        }
     */
    @GetMapping("/getRecensioniBarbiere/{idBarbiere}")
    public ResponseEntity<?> getRecensioniBarbiere(@PathVariable("idBarbiere") Long idBarbiere) {
        return ResponseEntity.ok(recensioneRepository.findAllByPrenotazione_Barbiere_Id(idBarbiere));
    }

    /*
    Metodo che restituisce tutti i prodotti
     */
    @GetMapping("/getProdotti")
    public ResponseEntity<?> getProdotti() {
        return ResponseEntity.ok(prodottoRepository.findAll());
    }

    /*
     *  METODI PATCH
     */

    /*
        ESEMPIO DI JSON
        {
            "idPrenotazione":"1",                   OBBLIGATORIO
            "nome":"Giorgio Carletti",
            "mail":"giorgio.carletti@gmail.com",
            "idBarbiere":"1",
            "startTime":"25/08/2021 14:30",
            "trattamenti":"1,2,3"
        }
    */
    @PatchMapping("/modificaPrenotazione")
    public ResponseEntity<?> modificaPrenotazione(@RequestBody Map<String,String> payload) {
        List<String> messages = new ArrayList<>();
        if(payload.containsKey("idPrenotazione") && prenotazioneRepository.findById(Long.parseLong(payload.get("idPrenotazione")))!=null){
            Prenotazione prenotazione = prenotazioneRepository.findById(Long.parseLong(payload.get("idPrenotazione")));

            //Verifica validità del nome
            if(!payload.get("nome").equals("")) {
                prenotazione.setNome(payload.get("nome"));
            } else {
                messages.add("Nome non valido.");
            }

            //Verifica validità della mail
            if(!payload.get("mail").equals("")){
                prenotazione.setMail(payload.get("mail"));
            } else {
                messages.add("Mail non valida.");
            }

            //Verifica validità del barbiere
            if(utenteRepository.findByIdAndRoleEquals(Long.parseLong(payload.get("idBarbiere")), "ROLE_BARBIERE") != null) {
                prenotazione.setBarbiere(utenteRepository.findById(Long.parseLong(payload.get("idBarbiere"))));
            } else {
                messages.add("Barbiere non valido.");
            }

            //Verifica validità della data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            try {
                LocalDateTime start = LocalDateTime.parse(payload.get("startTime"), formatter);
                if(start.isAfter(LocalDateTime.now())){
                    prenotazione.setStartTime(start);
                } else {
                    messages.add("Orario non valido.");
                }
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Campi superflui alla creazione della prenotazione
            if(payload.containsKey("trattamenti") && payload.get("trattamenti")!="") {
                prenotazione.resetTrattamenti();
                for (String idTrattamento : new ArrayList<String>(Arrays.asList(payload.get("trattamenti").split(",")))) {
                    if (trattamentoRepository.findById(Long.parseLong(idTrattamento)) != null) {
                        //Aggiunta dei trattamenti alla prenotazione
                        try {
                            prenotazione.addTrattamento(trattamentoRepository.findById(Long.parseLong(idTrattamento)));
                        }catch (Exception e){
                            messages.add(e.toString());
                        }
                    } else {
                        messages.add("Il trattamento con id '" + idTrattamento + "' non è valido.");
                    }
                }
            }

            //Calcolo durata e prezzo prenotazione tramite trattamenti
            Double prezzo = 0.0;
            LocalDateTime durataTaglio = prenotazione.getStartTime();
            for (Trattamento trattamento : prenotazione.getTrattamenti()) {
                durataTaglio=durataTaglio.plusMinutes(trattamento.getDurata());
                prezzo+=trattamento.getPrezzo();
            }
            try {
                prenotazione.setEndTime(durataTaglio);
                prenotazione.setPrezzo(prezzo);
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Salvataggio prenotazione e risposta
            try {
                prenotazioneRepository.save(prenotazione);
                //Invio email automatiche
                emailSender.emailNuovaPrenotazione(prenotazione);

                return ResponseEntity.ok(prenotazione);
            } catch (Exception e){
                messages.add(e.toString());
            }

        }else{
            messages.add("Campi insufficienti alla modifica della prenotazione");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
     *  METODI DELETE
     */

    /*
    ESEMPIO DI JSON
    {
        "idPrenotazione":"1"                    OBBLIGATORIO
    }
*/
    @DeleteMapping("/eliminaPrenotazione/{idPrenotazione}")
    public ResponseEntity<?> eliminaPrenotazione(@PathVariable ("idPrenotazione") Long idPrenotazione) {
        if(idPrenotazione!=null && prenotazioneRepository.findById(idPrenotazione) != null){
            Prenotazione prenotazione = prenotazioneRepository.findById(idPrenotazione);
            prenotazioneRepository.delete(prenotazione);
            //Email automatiche
            emailSender.emailPrenotazioneEliminata(prenotazione);

            return ResponseEntity.ok(new Gson().toJson("Prenotazione eliminata."));
        } else {
            return ResponseEntity.badRequest().body("Prenotazione inesistente");
        }
    }
}
