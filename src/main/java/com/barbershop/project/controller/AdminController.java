package com.barbershop.project.controller;

import com.barbershop.project.model.Indirizzo;
import com.barbershop.project.model.Prodotto;
import com.barbershop.project.model.Trattamento;
import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//CHIAMATE POST CHE PUO' EFFETTUARE SOLAMENTE L'ADMIN
@Transactional
@RestController
@CrossOrigin(origins = "${frontend.path}")
@RequestMapping("/admin")
public class AdminController{
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    TrattamentoRepository trattamentoRepository;
    @Autowired
    ProdottoRepository prodottoRepository;
    @Autowired
    OrdineRepository ordineRepository;
    @Autowired
    IndirizzoRepository indirizzoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;

    /*
     *  METODI POST
     */

    /*
    Metodo che crea nuovi utenti amministratori

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
    @PostMapping("/nuovoAdmin")
    public ResponseEntity<?> nuovoAdmin(@RequestBody Map<String, String> payload) {
        //Creo una lista di messaggi di errore
        List<String> messages = new ArrayList<String>();

        //Check se l'utente esiste
        if (utenteRepository.findByMail(payload.get("mail")) != null) {
            messages.add("Mail già utilizzata.");
        }
        //Check che tutti i valori obbligatori siano stati inviati
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

            //Imposto ruolo come ROLE_ADMIN
            try {
                utente.setRole("ROLE_ADMIN");
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Creo un indirizzo fittizio per l'ipotetico negozio del barbiere
            Indirizzo indirizzo = new Indirizzo();
            indirizzo.setUtente(utente);
            indirizzo.setCitta("Roma");
            indirizzo.setVia("Via Roma");
            indirizzo.setNumeroCivico("1");
            indirizzo.setCap("00100");
            try {
                indirizzoRepository.save(indirizzo);
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
    Metodo che crea nuovi utenti barbieri

        ESEMPIO DI JSON
        {
            "mail":"mariorossi@mail.com",       OBBLIGATORIO
            "password":"password",              OBBLIGATORIO
            "nome":"Mario",                     OBBLIGATORIO
            "cognome":"Rossi",
            "cellulare":"1234567890",
            "foto":"C:\\Users\\utente\\Pictures\\pic.jpg"
        }
    */
    @PostMapping("/nuovoBarbiere")
    public ResponseEntity<?> nuovoBarbiere(@RequestBody Map<String, String> payload) {
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<String>();

        //Check se l'utente esiste
        if (utenteRepository.findByMail(payload.get("mail")) != null) {
            messages.add("Mail già utilizzata.");
        }
        //Check se tutti i valori obbligatori sono stati inviati
        else if(payload.containsKey("mail") && payload.containsKey("password") && payload.containsKey("nome")){
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

            //Verifica campo nome non vuoto e set
            if (!payload.get("nome").equals("")) {
                try {
                    utente.setNome(payload.get("nome"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            //Imposto ruolo come ROLE_BARBIERE
            try {
                utente.setRole("ROLE_BARBIERE");
            }catch (Exception e){
                messages.add(e.toString());
            }

            //Campi superflui alla creazione dell'utente
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
            messages.add("Campi insufficienti alla creazione di un barbiere.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che crea nuovi trattamenti per i capelli

        ESEMPIO DI JSON
        {
            "nome":"Shampoo",                       OBBLIGATORIO
            "prezzo":"5.0",                         OBBLIGATORIO
            "durata":"5"
        }
     */
    @PostMapping("/nuovoTrattamento")
    public ResponseEntity<?> nuovoTrattamento(@RequestBody Map<String, String> payload){
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        //Check se i valori obbligatori sono stati inviati
        if(payload.containsKey("nome") && payload.containsKey("prezzo")) {
            Trattamento trattamento = new Trattamento();

            //Verifica campo nome non vuoto e set
            if (!payload.get("nome").equals("")) {
                try {
                    trattamento.setNome(payload.get("nome"));
                } catch (Exception e) {
                    messages.add(e.toString());
                }
            } else {
                messages.add("Nome non valido.");
            }

            //Verifica campo prezzo non vuoto e set
            if (!payload.get("prezzo").equals("")) {
                try {
                    trattamento.setPrezzo(Double.parseDouble(payload.get("prezzo")));
                } catch (Exception e) {
                    messages.add(e.toString());
                }
            } else {
                messages.add("Prezzo non valido.");
            }

            //Campi superflui alla creazione del trattamento
            if (payload.containsKey("durata")){
                if (!payload.get("durata").equals("")) {
                    try {
                        trattamento.setDurata(Integer.parseInt(payload.get("durata")));
                    } catch (Exception e) {
                        messages.add(e.toString());
                    }
                } else {
                    messages.add("Durata non valida.");
                }
            }

            try{
                //Salvataggio trattamento e risposta
                trattamentoRepository.save(trattamento);
                return ResponseEntity.ok(trattamento);
            }catch (Exception e){
                messages.add(e.toString());
            }
        } else {
            messages.add("Campi insufficienti alla creazione di un trattamento.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che crea un nuovo prodotto

        ESEMPIO DI JSON
        {
            "nome":"Tinta",                         OBBLIGATORIO
            "prezzo":"20.5",                        OBBLIGATORIO
            "descrizione":"Colorazione capelli rosso",
            "peso":"0.5",
            "foto":"C:\\Users\\utente\\Pictures\\pic.jpg"
        }
     */
    @PostMapping("/nuovoProdotto")
    public ResponseEntity<?> nuovoProdotto(@RequestBody Map<String, String> payload) throws UnexpectedRollbackException{
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        //Check se i valori obbligatori sono stati inviati
        if(payload.containsKey("nome") && payload.containsKey("prezzo")) {
            Prodotto prodotto = new Prodotto();

            //Verifica campo nome non vuoto e set
            if(!payload.get("nome").equals("")){
                try {
                    prodotto.setNome(payload.get("nome"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            } else {
                messages.add("Nome vuoto.");
            }

            //Verifica campo prezzo non vuoto e set
            if(!payload.get("prezzo").equals("")){
                try {
                    prodotto.setPrezzo(Double.parseDouble(payload.get("prezzo")));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            //Campi superflui alla creazione del prodotto
            if(payload.containsKey("descrizione")) {
                try {
                    prodotto.setDescrizione(payload.get("descrizione"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            if(payload.containsKey("peso")){
                try {
                    prodotto.setPeso(Double.parseDouble(payload.get("peso")));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            if(payload.containsKey("foto")){
                try {
                    prodotto.setFoto(payload.get("foto"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }

            try{
                prodottoRepository.save(prodotto);
                return ResponseEntity.ok(prodotto);
            }catch (Exception e){
                messages.add(e.toString());
            }

        }else {
            messages.add("Campi insufficienti alla creazione di un prodotto.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
     *  METODI GET
     */

    /*
    Metodo che restituisce tutti gli admin

        ESEMPIO DI JSON
        {}
     */
    @GetMapping("/getAdmin")
    public ResponseEntity<?> getAdmin(){
        List<String> messages = new ArrayList<>();
        try{
            return ResponseEntity.ok(utenteRepository.findAllByRoleEquals("ROLE_ADMIN"));
        } catch (Exception e){
            messages.add(e.toString());
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che restituisce tutti gli ordini
    */
    @GetMapping("/getOrdini")
    public ResponseEntity<?> getOrdini(){
        return ResponseEntity.ok(ordineRepository.findAll());
    }

    /*
    Metodo che restituisce il booleano se il ranking è attivo o meno
    */
    @GetMapping("/getRanking")
    public ResponseEntity<?> getRanking(){
        return ResponseEntity.ok(new Gson().toJson(Utente.isRanking()));
    }

    /*
     *  METODI PATCH
     */

    /*
Metodo che modifica i campi di un trattamento tramite id

    ESEMPIO DI JSON
    {
        "idTrattamento":1,                      OBBLIGATORIO
        "nome":"Shampoo",
        "prezzo":"5.0",
        "durata":"5"
    }
 */
    @PatchMapping("/modificaTrattamento")
    public ResponseEntity<?> modificaTrattamento(@RequestBody Map<String, String> payload){
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        //Check se i valori obbligatori sono stati inviati e se il trattamento esiste
        if (payload.containsKey("idTrattamento") && trattamentoRepository.findById(Long.parseLong(payload.get("idTrattamento")))!=null){
            Trattamento trattamento = trattamentoRepository.findById(Long.parseLong(payload.get("idTrattamento")));

            //Verifica quali campi sono inviati e set per la modifica
            if(payload.containsKey("nome")){
                if (payload.get("nome")!=null){
                    try {
                        trattamento.setNome(payload.get("nome"));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            if(payload.containsKey("prezzo")){
                if (payload.get("prezzo")!=null){
                    try {
                        trattamento.setPrezzo(Double.parseDouble(payload.get("prezzo")));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            if(payload.containsKey("durata")){
                if (payload.get("durata")!=null) {
                    try {
                        trattamento.setDurata(Integer.parseInt(payload.get("durata")));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            try {
                //Salvataggio trattamento e risposta
                trattamentoRepository.save(trattamento);
                return ResponseEntity.ok(trattamento);
            }catch (Exception e){
                messages.add(e.toString());
            }
        }else {
            messages.add("Trattamento inesistente");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che modifica i campi di un prodotto tramite id

        ESEMPIO DI JSON
        {
            "idProdotto":1,                        OBBLIGATORIO
            "nome":"Tinta",
            "prezzo":"20.5",
            "descrizione":"Colorazione capelli rosso",
            "peso":"0.5",
            "foto":"C:\\Users\\utente\\Pictures\\pic.jpg"
        }
     */
    @PatchMapping("/modificaProdotto")
    public ResponseEntity<?> modificaProdotto(@RequestBody Map<String, String> payload){
        List<String> messages = new ArrayList<>();
        if (payload.containsKey("idProdotto") && prodottoRepository.findById(Long.parseLong(payload.get("idProdotto")))!=null){
            Prodotto prodotto = prodottoRepository.findById(Long.parseLong(payload.get("idProdotto")));

            if(payload.containsKey("nome")){
                if (payload.get("nome")!=null){
                    try {
                        prodotto.setNome(payload.get("nome"));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            if(payload.containsKey("prezzo")){
                if (payload.get("prezzo")!=null){
                    try {
                        prodotto.setPrezzo(Double.parseDouble(payload.get("prezzo")));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            if(payload.containsKey("descrizione")){
                if (payload.get("descrizione")!=null) {
                    try {
                        prodotto.setDescrizione(payload.get("descrizione"));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            if(payload.containsKey("peso")){
                if (payload.get("peso")!=null) {
                    try {
                        prodotto.setPeso(Double.parseDouble(payload.get("peso")));
                    }catch (Exception e){
                        messages.add(e.toString());
                    }
                }
            }
            if(payload.containsKey("foto")){
                try {
                    prodotto.setFoto(payload.get("foto"));
                }catch (Exception e){
                    messages.add(e.toString());
                }
            }
            try {
                prodottoRepository.save(prodotto);
            }catch (Exception e){
                messages.add(e.toString());
            }
            return ResponseEntity.ok(prodotto);
        }else {
            messages.add("Prodotto inesistente");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo per attivare o disattivare il ranking tra i barbieri
     */
    @PatchMapping("/toggleRanking")
    public ResponseEntity<?> toggleRanking(){
        List<String> messages = new ArrayList<>();
        try {
            Utente.toggleRanking();
            for (Utente admin: utenteRepository.findAllByRoleEquals("ROLE_ADMIN")) {
                utenteRepository.save(admin);
            }
            return ResponseEntity.ok(new Gson().toJson(Utente.isRanking()));
        }catch (Exception e){
            messages.add(e.toString());
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
     *  METODI DELETE
     */

    /*
    Metodo che elimina un utente amministratore tramite id
     */
    @DeleteMapping("/eliminaAdmin/{idAdmin}")
    public ResponseEntity<?> eliminaAdmin(@PathVariable("idAdmin") Long idAdmin){
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();

        //Controlla che esista almeno un altro amministratore prima di effettuare l'eliminazione
        if(utenteRepository.findAllByRoleEquals("ROLE_ADMIN").size()>1) {
            //Controllo dei campi obbligatori e check se l'utente esiste
            if (idAdmin!=null && utenteRepository.findById(idAdmin)!=null && utenteRepository.findById(idAdmin).getRole().equals("ROLE_ADMIN")) {
                try {
                    //Eliminazione utente e risposta
                    utenteRepository.delete(utenteRepository.findById(idAdmin));
                    return ResponseEntity.ok(utenteRepository.findAllByRoleEquals("ROLE_ADMIN"));
                } catch (Exception e) {
                    messages.add(e.toString());
                }
            } else {
                messages.add("Admin inesistente.");
            }
        } else {
            messages.add("Impossibile cancellare l'unico amministratore, crearne uno prima.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che elimina un utente barbiere tramite id
     */
    @DeleteMapping("/eliminaBarbiere/{idBarbiere}")
    public ResponseEntity<?> eliminaBarbiere(@PathVariable("idBarbiere") Long idBarbiere) {
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        //Check se i valori obbligatori sono stati inviati e se l'utente esiste ed ha come ruolo barbiere
        if (idBarbiere!=null && utenteRepository.findById(idBarbiere)!=null && utenteRepository.findById(idBarbiere).getRole().equals("ROLE_BARBIERE")) {
            try {
                //Eliminazione utente e risposta
                utenteRepository.delete(utenteRepository.findById(idBarbiere));
                return ResponseEntity.ok(utenteRepository.findAllByRoleEquals("ROLE_BARBIERE"));
            } catch (Exception e) {
                messages.add(e.toString());
            }
        } else {
            messages.add("Barbiere inesistente.");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che elimina un trattamento tramite id
    */
    @DeleteMapping("/eliminaTrattamento/{idTrattamento}")
    public ResponseEntity<?> eliminaTrattamento(@PathVariable("idTrattamento") Long idTrattamento){
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        //Check se i valori obbligatori sono stati inviati e se il trattamento esiste
        if(idTrattamento!=null && trattamentoRepository.findById(idTrattamento) != null){
            try {
                //Eliminazione trattamento e risposta
                trattamentoRepository.delete(trattamentoRepository.findById(idTrattamento));
                return ResponseEntity.ok(trattamentoRepository.findAll());
            }catch (Exception e){
                messages.add(e.toString());
            }
        } else {
            messages.add("Trattamento inesistente");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo che elimina un prodotto tramite id
    */
    @DeleteMapping("/eliminaProdotto/{idProdotto}")
    public ResponseEntity<?> eliminaProdotto(@PathVariable("idProdotto") Long idProdotto){
        List<String> messages = new ArrayList<>();
        if(idProdotto!=null && prodottoRepository.findById(idProdotto)!=null){
            try {
                prodottoRepository.delete(prodottoRepository.findById(idProdotto));
                return ResponseEntity.ok(prodottoRepository.findAll());
            }catch (Exception e){
                messages.add(e.toString());
            }
        } else {
            messages.add("Prodotto inesistente");
        }
        return ResponseEntity.badRequest().body(messages);
    }
}
