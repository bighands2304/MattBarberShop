package com.barbershop.project.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(EmailDetails details)
    {

        //Verifica eccezioni
        try {
            //Crea un mail di testo
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            //Set dei dettagli della mail
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            //Invio mail
            javaMailSender.send(mailMessage);
            return "Mail inviata con successo";
        }

        //
        catch (Exception e) {
            return "Errore nell'invio della mail";
        }
    }
}
