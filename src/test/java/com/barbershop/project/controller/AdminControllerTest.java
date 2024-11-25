package com.barbershop.project.controller;

import com.barbershop.project.repository.ProdottoRepository;
import com.barbershop.project.repository.TrattamentoRepository;
import com.barbershop.project.repository.UtenteRepository;
import com.barbershop.project.security.JwtUtil;
import com.barbershop.project.security.UserDetailsServiceImpl;
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
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private TrattamentoRepository trattamentoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    @Test
    public void UnuthorizedTest() throws Exception {
        String json =   "{" +
                "            \"mail\":\"nuovoadmintest@mail.com\"," +
                "            \"password\":\"password\"," +
                "            \"nome\":\"Test\"," +
                "            \"cognome\":\"Test\"," +
                "            \"cellulare\":\"1234567890\"," +
                "            \"foto\":\"\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/nuovoAdmin")
                        //.header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        System.out.println(result.getResponse().getStatus() + " - " + result.getResponse().getErrorMessage());
    }

    @Test
    public void nuovoAdminTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        String json =   "{" +
                "            \"mail\":\"nuovoadmintest@mail.com\"," +
                "            \"password\":\"password\"," +
                "            \"nome\":\"Test\"," +
                "            \"cognome\":\"Test\"," +
                "            \"cellulare\":\"1234567890\"," +
                "            \"foto\":\"\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/nuovoAdmin")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("id")))
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void nuovoBarbiereTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        String json =   "{" +
                "            \"mail\":\"nuovobarbieretest@mail.com\"," +
                "            \"password\":\"password\"," +
                "            \"nome\":\"Test\"," +
                "            \"cognome\":\"Test\"," +
                "            \"cellulare\":\"1234567890\"," +
                "            \"foto\":\"\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/nuovoBarbiere")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void nuovoTrattamentoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        String json =   "{" +
                "            \"nome\":\"Test\"," +
                "            \"prezzo\":\"5.0\"," +
                "            \"durata\":\"5\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/nuovoTrattamento")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("id")))
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void nuovoProdottoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        String json =   "{" +
                "            \"nome\":\"Test\"," +
                "            \"prezzo\":\"20.5\"," +
                "            \"descrizione\":\"Caso di test\"," +
                "            \"peso\":\"0.5\"," +
                "            \"foto\":\"C\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/nuovoProdotto")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getAdminTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAdmin")
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getOrdiniTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/getOrdini")
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getRankingTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/getRanking")
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void modificaTrattamentoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (trattamentoRepository.findAll().isEmpty())
            nuovoTrattamentoTest();
        Long idTrattamento = trattamentoRepository.findAll().iterator().next().getId();

        String json =   "{" +
                "            \"idTrattamento\":\""+ idTrattamento +"\"," +
                "            \"nome\":\"Test\"," +
                "            \"prezzo\":\"5.0\"," +
                "            \"durata\":\"5\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/admin/modificaTrattamento")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void modificaProdottoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (prodottoRepository.findAll().isEmpty())
            nuovoProdottoTest();
        Long idProdotto = prodottoRepository.findAll().iterator().next().getId();

        String json =   "{" +
                "            \"idProdotto\":\"" + idProdotto + "\"," +
                "            \"nome\":\"Test\"," +
                "            \"prezzo\":\"20.5\"," +
                "            \"descrizione\":\"Caso di test\"," +
                "            \"peso\":\"0.5\"," +
                "            \"foto\":\"C\"" +
                "        }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/admin/modificaProdotto")
                        .header("Authorization", "Bearer " + token )
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void ToggleRankingTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/admin/toggleRanking")
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        result = mockMvc.perform(MockMvcRequestBuilders.patch("/admin/toggleRanking")
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void EliminaAdminTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_ADMIN").size()==1) {
            Long idAdmin = utenteRepository.findAllByRoleEquals("ROLE_ADMIN").iterator().next().getId();

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaAdmin/" + idAdmin)
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Impossibile cancellare l'unico amministratore, crearne uno prima.")))
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }else {
            if (utenteRepository.findAllByRoleEquals("ROLE_ADMIN").size() == 1)
                nuovoAdminTest();
            utenteRepository.findAllByRoleEquals("ROLE_ADMIN").iterator().next(); //scorre di una posizione
            Long idAdmin = utenteRepository.findAllByRoleEquals("ROLE_ADMIN").iterator().next().getId();

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaAdmin/" + idAdmin)
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }
    }

    @Test
    public void EliminaBarbiereTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").isEmpty())
            nuovoBarbiereTest();
        Long idBarbiere = utenteRepository.findAllByRoleEquals("ROLE_BARBIERE").iterator().next().getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaBarbiere/" + idBarbiere)
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void EliminaTrattamentoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (trattamentoRepository.findAll().isEmpty())
            nuovoTrattamentoTest();
        Long idTrattamento = trattamentoRepository.findAll().iterator().next().getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaTrattamento/" + idTrattamento)
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void EliminaProdottoTest() throws Exception {
        String token= jwtUtil.generateToken(userDetailsService.loadUserByUsername("admin"));

        if (prodottoRepository.findAll().isEmpty())
            nuovoProdottoTest();
        Long idProdotto = prodottoRepository.findAll().iterator().next().getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaProdotto/" + idProdotto)
                        .header("Authorization", "Bearer " + token )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


}
