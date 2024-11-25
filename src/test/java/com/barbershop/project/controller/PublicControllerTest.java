package com.barbershop.project.controller;

import com.barbershop.project.model.Prenotazione;
import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.PrenotazioneRepository;
import com.barbershop.project.repository.TrattamentoRepository;
import com.barbershop.project.repository.UtenteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class PublicControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    TrattamentoRepository trattamentoRepository;
    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    @Test
    public void nuovoUtenteTest() throws Exception {
        String json =   "{" +
                "            \"mail\":\"utenteTest@mail.com\"," +
                "            \"password\":\"passwordTesting\"," +
                "            \"nome\":\"Test\"," +
                "            \"cognome\":\"Test\"," +
                "            \"cellulare\":\"1234567890\"," +
                "            \"foto\":\"\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/public/nuovoUtente")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void nuovaPrenotazioneTest() throws Exception {
        Utente barbiere = new Utente();
        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty()) {
            barbiere.setMail("testbaber");
            barbiere.setPassword("passtest");
            barbiere.setNome("test");
            barbiere.setRole("ROLE_BARBIERE");
            utenteRepository.save(barbiere);
        }

        barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();
        String json =   "{" +
                "            \"nome\":\"testing\"," +
                "            \"mail\":\"testmail@mail.com\"," +
                "            \"idBarbiere\":\"" + barbiere.getId() + "\"," +
                "            \"startTime\":\"25/08/2023 16:30\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/public/nuovaPrenotazione")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void recensionePrenotazioneTest() throws Exception {
        if (prenotazioneRepository.findAll().isEmpty()){
            nuovaPrenotazioneTest();
        }
        Prenotazione prenotazione = prenotazioneRepository.findAll().iterator().next();

        String json =   "{" +
                "            \"idPrenotazione\":\"" + prenotazione.getId() + "\"," +
                "            \"valutazione\":\"3.5\"," +
                "            \"descrizione\":\"Nulla di che\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/public/recensionePrenotazione")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getBarbieriTest() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getBarbieri")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getBarbiereTest() throws Exception {
        Utente barbiere = new Utente();
        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty()){
            barbiere.setMail("testbaber");
            barbiere.setPassword("passtest");
            barbiere.setNome("test");
            barbiere.setRole("ROLE_BARBIERE");
            utenteRepository.save(barbiere);
        }
        barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getBarbiere/" + barbiere.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPrenotazioniTest() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getPrenotazioni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPrenotazioneTest() throws Exception {
        if (prenotazioneRepository.findAll().isEmpty()){
            nuovaPrenotazioneTest();
        }
        Prenotazione prenotazione = prenotazioneRepository.findAll().iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getPrenotazione/" + prenotazione.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPrenotazioniBarbiereTest() throws Exception {
        Utente barbiere = new Utente();
        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty()){
            barbiere.setMail("testbaber");
            barbiere.setPassword("passtest");
            barbiere.setNome("test");
            barbiere.setRole("ROLE_BARBIERE");
            utenteRepository.save(barbiere);
        }
        barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getPrenotazioniBarbiere/" + barbiere.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPrenotazioniBarbierePerGiornoTest() throws Exception {
        Utente barbiere = new Utente();
        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty()){
            barbiere.setMail("testbaber");
            barbiere.setPassword("passtest");
            barbiere.setNome("test");
            barbiere.setRole("ROLE_BARBIERE");
            utenteRepository.save(barbiere);
        }
        barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getPrenotazioniBarbierePerGiorno/" + barbiere.getId() + "/25-08-2023")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPrenotazioniLibereBarbierePerGiornoTest() throws Exception {
        Utente barbiere = new Utente();
        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty()){
            barbiere.setMail("testbaber");
            barbiere.setPassword("passtest");
            barbiere.setNome("test");
            barbiere.setRole("ROLE_BARBIERE");
            utenteRepository.save(barbiere);
        }
        barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getPrenotazioniLibereBarbierePerGiorno/" + barbiere.getId() + "/25-08-2023")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getTrattamentiTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getTrattamenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getRecensioniTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getRecensioni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getLastThreeRecensioniTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getLastThreeRecensioni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getRecensioniBarbiereTest() throws Exception {
        Utente barbiere = new Utente();
        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty()){
            barbiere.setMail("testbaber");
            barbiere.setPassword("passtest");
            barbiere.setNome("test");
            barbiere.setRole("ROLE_BARBIERE");
            utenteRepository.save(barbiere);
        }
        barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getRecensioniBarbiere/" +barbiere.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getProdottiTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/public/getProdotti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void modificaPrenotazioneTest() throws Exception {
        if (prenotazioneRepository.findAll().isEmpty()){
            nuovaPrenotazioneTest();
        }
        Prenotazione prenotazione = prenotazioneRepository.findAll().iterator().next();
        Utente barbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next();

        String json =   "{" +
                "            \"idPrenotazione\":\""+prenotazione.getId()+"\"," +
                "            \"nome\":\"testUtente\"," +
                "            \"mail\":\"mailtest@test.com\"," +
                "            \"idBarbiere\":\""+barbiere.getId()+"\"," +
                "            \"startTime\":\"25/08/2023 17:30\"" +
                "       }";
        System.out.println(json);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/public/modificaPrenotazione/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void eliminaPrenotazioneTest() throws Exception {

        if (prenotazioneRepository.findAll().isEmpty()){
            nuovaPrenotazioneTest();
        }
        Prenotazione prenotazione = prenotazioneRepository.findAll().iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/public/eliminaPrenotazione/" + prenotazione.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
