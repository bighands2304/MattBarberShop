package com.barbershop.project.controller;

import com.barbershop.project.model.Prodotto;
import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.IndirizzoRepository;
import com.barbershop.project.repository.ProdottoRepository;
import com.barbershop.project.repository.UtenteRepository;
import com.barbershop.project.security.JwtUtil;
import com.barbershop.project.security.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Test
    public void nuovoIndirizzoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        Utente user;
        if(utenteRepository.findAllByRoleEquals("ROLE_USER").isEmpty()) {
            user = new Utente();
            user.setMail("userTest");
            user.setPassword(passwordEncoder.encode("passTest"));
            user.setRole("ROLE_USER");
            utenteRepository.save(user);
        }
        user = utenteRepository.findAllByRoleEquals("ROLE_USER").iterator().next();

        String json =   "{" +
                "            \"idUtente\":\"" + user.getId() + "\"," +
                "            \"citta\":\"Roma\"," +
                "            \"cap\":\"00100\"," +
                "            \"via\":\"Via Roma\"," +
                "            \"numeroCivico\":\"1\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/nuovoIndirizzo")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void nuovoOrdineTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_USER").isEmpty()){
            nuovoIndirizzoTest();
        }

        Utente destinatario =utenteRepository.findAllByRoleEquals("ROLE_USER").iterator().next();
        if (indirizzoRepository.findAllByUtente(destinatario).isEmpty())
            nuovoIndirizzoTest();
        Long idIndirizzo = indirizzoRepository.findAllByUtente(destinatario).iterator().next().getId();

        Prodotto prodotto = new Prodotto();
        prodotto.setNome("prodottoTest");
        prodotto.setPrezzo(5D);
        prodottoRepository.save(prodotto);

        String json =   "{" +
                "            \"idIndirizzoDestinatario\":\"" + idIndirizzo + "\"," +
                "            \"prodotti\":\""+prodotto.getId()+ "\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/nuovoOrdine")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getOrdiniDestinatarioTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_USER").isEmpty()){
            nuovoOrdineTest();
        }

        Utente destinatario = utenteRepository.findAllByRoleEquals("ROLE_USER").iterator().next();


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getOrdiniDestinatario/" + destinatario.getId())
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getIndirizziUtenteTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_USER").isEmpty()){
            nuovoIndirizzoTest();
        }
        Utente utente = utenteRepository.findAllByRoleEquals("ROLE_USER").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getIndirizziUtente/" + utente.getId())
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void toggleNotificheTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_USER").isEmpty()){
            nuovoIndirizzoTest();
        }
        Utente utente = utenteRepository.findAllByRoleEquals("ROLE_USER").iterator().next();

        String json="{" +
                "            \"idUtente\":\"1\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/user/toggleNotifiche")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        result = mockMvc.perform(MockMvcRequestBuilders.patch("/user/toggleNotifiche")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void eliminaUtenteTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_USER").isEmpty()){
            nuovoIndirizzoTest();
        }
        Utente utente = utenteRepository.findAllByRoleEquals("ROLE_USER").iterator().next();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/eliminaUtente/" + utente.getId())
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
