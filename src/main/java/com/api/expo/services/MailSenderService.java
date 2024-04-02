package com.api.expo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.api.expo.models.User;
import com.api.expo.models.Validation;

@Service
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplie@dokumiton.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Votre code d'activation!");
        String text = String.format(" Bonjour %s . Bienvenue sur DOKUMITON. Pour continuer votre inscription, veillez copier et coller le code suivant: %s . " +
                                     "", validation.getUser().getFirstname(), validation.getCode());
        message.setText(text);
        mailSender.send(message);
    }

    void sendResetEmail(User user, String resetToken) {
         String resetLink = "http://example.com/reset-password?token=" + resetToken;
         String emailContent = "Bonjour " + user.getFirstname() + ",\n\n" +
                               "Vous avez demandé à réinitialiser votre mot de passe. " +
                               "Veuillez cliquer sur le lien ci-dessous pour réinitialiser votre mot de passe :\n" +
                               resetLink + "\n\n" +
                               "Si vous n'avez pas demandé cette réinitialisation, vous pouvez ignorer cet e-mail.\n\n" +
                               "Cordialement,\n" +
                               "Votre équipe de support.";
         
         SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom("noreply@dokumiton.com");
         message.setTo(user.getEmail());
         message.setSubject("Modification de mot de passe!");
         message.setText(emailContent);
         mailSender.send(message);
 }
}
