package com.barbershop.project.controller;

import com.barbershop.project.email.EmailSender;
import com.barbershop.project.model.Indirizzo;
import com.barbershop.project.model.Ordine;
import com.barbershop.project.model.Prodotto;
import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@CrossOrigin(origins = "${frontend.path}")
@RequestMapping("/user")
public class UserController {

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
    EmailSender emailSender;

    /*
     *  METODI POST
     */

    /*
    Metodo per la creazione di un nuovo ordine

        ESEMPIO DI JSON
        {
            "idIndirizzoDestinatario":"2",      OBBLIGATORIO
            "prodotti":"1,2"                    OBBLIGATORIO
        }
    */
    @PostMapping("/nuovoOrdine")
    public ResponseEntity<?> nuovoOrdine(@RequestBody Map<String, String> payload){
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();

        //Check che tutti i valori obbligatori siano stati inviati
        if(payload.containsKey("idIndirizzoDestinatario") && payload.containsKey("prodotti")){
            //Inizializzo il nuovo ordine
            Ordine ordine = new Ordine();
            //Set del mittente e del destinatario
            try {
                ordine.setMittente(utenteRepository.findAllByRoleEquals("ROLE_ADMIN").iterator().next().getIndirizzo().get(0));
                ordine.setDestinatario(indirizzoRepository.findIndirizzoById(Long.parseLong(payload.get("idIndirizzoDestinatario"))));
            } catch (Exception e){
                messages.add(e.toString());
            }

            //Creo una lista di prodotti
            List<Prodotto> prodotti = new ArrayList<>();
            //Ciclo per memorizzare i prodotti tramite id
            for (String prodottoId: new ArrayList<String>(Arrays.asList(payload.get("prodotti").split(",")))) {
                //Check se il prodotto esiste
                if(prodottoRepository.findById(Long.parseLong(prodottoId))!=null){
                    prodotti.add(prodottoRepository.findById(Long.parseLong(prodottoId)));
                } else {
                    messages.add("Il prodotto con id: " + prodottoId + "non esiste.");
                }
            }
            //Verifico che la lista di prodotti non sia vuota
            if(!prodotti.isEmpty()){
                //Set dei prodotti e salvataggio ordine con risposta
                try{
                    ordine.setProdotti(prodotti);
                    ordineRepository.save(ordine);

                    //Invio mail automatiche
                    emailSender.emailNuovoOrdine(ordine);

                    return ResponseEntity.ok(ordine);
                }catch (Exception e){
                    messages.add(e.toString());
                    return ResponseEntity.badRequest().body(messages);
                }

            } else {
                messages.add("Nessun prodotto inserito");
            }
        } else {
            messages.add("Campi insufficienti a creare l'ordine");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
    Metodo per la creazione di un nuovo indirizzo

        ESEMPIO DI JSON
        {
            "idUtente":"1",                     OBBLIGATORIO
            "citta":"Roma",                     OBBLIGATORIO
            "cap":"00100",                      OBBLIGATORIO
            "via":"Via Roma",                   OBBLIGATORIO
            "numeroCivico":"1"                  OBBLIGATORIO
        }
    */
    @PostMapping("/nuovoIndirizzo")
    public ResponseEntity<?> nuovoIndirizzo(@RequestBody Map<String, String> payload){
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        if(payload.containsKey("idUtente") && utenteRepository.findById(Long.parseLong(payload.get("idUtente")))!=null && payload.containsKey("citta") && payload.containsKey("cap") && payload.containsKey("via") && payload.containsKey("numeroCivico")){
            //Inizializzo l'indirizzo
            Indirizzo indirizzo = new Indirizzo();

            try {
                indirizzo.setUtente(utenteRepository.findById(Long.parseLong(payload.get("idUtente"))));
                indirizzo.setCitta(payload.get("citta"));
                indirizzo.setCap(payload.get("cap"));
                indirizzo.setVia(payload.get("via"));
                indirizzo.setNumeroCivico(payload.get("numeroCivico"));
            } catch (Exception e){
                messages.add(e.toString());
            }
            try {
                indirizzoRepository.save(indirizzo);
                return ResponseEntity.ok(indirizzo);
            } catch (Exception e){
                messages.add(e.toString());
            }
        } else {
            messages.add("Campi insufficienti alla creazione dell'indirizzo");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
     *  METODI GET
     */

    /*
    Metodo che restituisce tutti gli ordini relativi ad un utente tramite id utente
    */
    @GetMapping("/getOrdiniDestinatario/{idUtente}")
    public ResponseEntity<?> getOrdiniDestinatario(@PathVariable("idUtente") Long idUtente) {
        List<Ordine> ordini = ordineRepository.findAllByDestinatario_Utente_Id(idUtente);
        return ResponseEntity.ok(ordini);
    }

    /*
    Metodo che restituisce tutti gli indirizzi relativi ad un utente tramite id utente
    */
    @GetMapping("/getIndirizziUtente/{idUtente}")
    public ResponseEntity<?> getIndirizziUtente(@PathVariable("idUtente") Long idUtente) {
        ArrayList<Indirizzo> indirizzi = indirizzoRepository.findAllByUtente(utenteRepository.findById(idUtente));
        return ResponseEntity.ok(indirizzi);
    }

    /*
     *  METODI PATCH
     */

    /*
    Metodo per attivare o disattivare le notifiche

        ESEMPIO DI JSON
        {
            "idUtente":"1"
        }
     */
    @PatchMapping("/toggleNotifiche")
    public ResponseEntity<?> toggleNotifiche(@RequestBody Map<String, String> payload){
        List<String> messages = new ArrayList<>();
        if (payload.containsKey("idUtente") && utenteRepository.findById(Long.parseLong(payload.get("idUtente")))!=null) {
            try {
                Utente utente = utenteRepository.findById(Long.parseLong(payload.get("idUtente")));
                utente.toggleNotifiche();
                utenteRepository.save(utente);
                return ResponseEntity.ok(new Gson().toJson(utente.isNotifiche()));
            }catch (Exception e){
                messages.add(e.toString());
            }
        }else {
            messages.add("Utente inesistente");
        }
        return ResponseEntity.badRequest().body(messages);
    }

    /*
     *  METODI DELETE
     */

    /*
    Metodo che elimina un utente user tramite id
    */
    @DeleteMapping("/eliminaUtente/{idUtente}")
    public ResponseEntity<?> eliminaUtente(@PathVariable("idUtente") Long idUtente) {
        //Creo una lista di messaggi d'errore
        List<String> messages = new ArrayList<>();
        //Check se i valori obbligatori sono stati inviati e se l'utente esiste ed ha come ruolo user
        if (idUtente!=null && utenteRepository.findById(idUtente)!=null && utenteRepository.findById(idUtente).getRole().equals("ROLE_USER")) {
            try {
                //Eliminazione utente e risposta
                utenteRepository.delete(utenteRepository.findById(idUtente));
                return ResponseEntity.ok(utenteRepository.findAllByRoleEquals("ROLE_USER"));
            } catch (Exception e) {
                messages.add(e.toString());
            }
        } else {
            messages.add("Utente inesistente.");
        }
        return ResponseEntity.badRequest().body(messages);
    }
}
