package com.barbershop.project.controller;

import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.UtenteRepository;
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

//https://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services
//https://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteRepository utenteRepository;

    @Test
    public void loginAdminTest() throws Exception {
        Utente admin = new Utente();
        admin.setMail("utenteAdmin");
        admin.setPassword(passwordEncoder.encode("passwordAdmin"));
        admin.setRole("ROLE_ADMIN");
        utenteRepository.save(admin);

        String json =   "{" +
                            "\"username\":\"utenteAdmin\"," +
                            "\"password\":\"passwordAdmin\"" +
                        "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("jwt")))
                        .andExpect(content().string(containsString("ROLE_ADMIN")))
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void loginBarbiereTest() throws Exception {
        Utente barbiere = new Utente();
        barbiere.setMail("utenteBabriere");
        barbiere.setPassword(passwordEncoder.encode("passwordBarbiere"));
        barbiere.setRole("ROLE_BARBIERE");
        utenteRepository.save(barbiere);

        String json =   "{" +
                            "\"username\":\"utenteBabriere\"," +
                            "\"password\":\"passwordBarbiere\"" +
                        "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("jwt")))
                        .andExpect(content().string(containsString("ROLE_BARBIERE")))
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void loginUserTest() throws Exception {
        Utente utente = new Utente();
        utente.setMail("utenteUtente");
        utente.setPassword(passwordEncoder.encode("passwordUtente"));
        utente.setRole("ROLE_USER");
        utenteRepository.save(utente);

        String json =   "{" +
                            "\"username\":\"utenteUtente\"," +
                            "\"password\":\"passwordUtente\"" +
                        "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("jwt")))
                        .andExpect(content().string(containsString("ROLE_USER")))
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void wrongLoginTest() throws Exception {
        String json =   "{" +
                            "\"username\":\"userErrato\"," +
                            "\"password\":\"passwordErrata\"" +
                        "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        System.out.println(result.getResponse().getStatus() + " - " + result.getResponse().getErrorMessage());
    }
}
