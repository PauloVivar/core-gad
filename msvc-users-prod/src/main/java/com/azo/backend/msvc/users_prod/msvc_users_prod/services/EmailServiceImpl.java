package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

import org.eclipse.angus.mail.smtp.SMTPTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azo.backend.msvc.users_prod.msvc_users_prod.models.GmailOAuth2Provider;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;



//4. Cuarto Implementación de EmailService -> volver realidad el CRUD

@Service
public class EmailServiceImpl implements EmailService {

  // @Autowired
  // private JavaMailSender mailSender;
  // @Override
  // public void sendPasswordResetEmail(String to, String code) {
  //     SimpleMailMessage message = new SimpleMailMessage();
  //     message.setTo(to);
  //     message.setSubject("Restablecimiento de contraseña");
  //     message.setText("Para restablecer tu contraseña, utiliza el siguiente token: " + code +
  //                     "\nIngresa este token en la página de restablecimiento de contraseña.");
  //     mailSender.send(message);
  // }

  @Autowired
    private GmailOAuth2Provider gmailOAuth2Provider;

    @Override
    public void sendPasswordResetEmail(String to, String code) {
      try {
          Session session = gmailOAuth2Provider.getSession();
          MimeMessage message = new MimeMessage(session);
          message.setFrom(new InternetAddress("registrociudadano.azogues@gmail.com"));
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
          message.setSubject("Restablecimiento de contraseña");
          message.setText("Tu código de restablecimiento es: " + code);

          SMTPTransport transport = gmailOAuth2Provider.getSMTPTransport(session);
          transport.sendMessage(message, message.getAllRecipients());
          transport.close();
      } catch (Exception e) {
          // Manejo de errores
          e.printStackTrace();
      }
    }

}
