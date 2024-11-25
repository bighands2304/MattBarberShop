package com.barbershop.project;

import com.barbershop.project.model.Indirizzo;
import com.barbershop.project.model.Utente;
import com.barbershop.project.repository.IndirizzoRepository;
import com.barbershop.project.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@SpringBootApplication

@EnableJpaRepositories(basePackageClasses = UtenteRepository.class)
public class BarberShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberShopApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UtenteRepository utenteRepository;
	@Autowired
	IndirizzoRepository indirizzoRepository;

	//Non appena il server si avvia crea un utente admin di base,
	//se esiste gia un utente admin va avanti
	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void creaUtenteAdminDiBase() {
		if(utenteRepository.findAllByRoleEquals("ROLE_ADMIN").isEmpty()){
			Utente admin = new Utente();
			try {
				//Creo un admin di base con credenziali di default
				admin.setMail("admin");
				admin.setPassword(passwordEncoder.encode("admin"));
				admin.setRole("ROLE_ADMIN");
				utenteRepository.save(admin);
			}catch (Exception e){}

			Indirizzo indirizzo = new Indirizzo();
			try {
				//Creo un indirizzo fittizio per l'ipotetico negozio del barbiere
				indirizzo.setUtente(admin);
				indirizzo.setCitta("Roma");
				indirizzo.setVia("Via Roma");
				indirizzo.setNumeroCivico("1");
				indirizzo.setCap("00100");
				indirizzoRepository.save(indirizzo);
			}catch (Exception e){}
		}
	}


}
