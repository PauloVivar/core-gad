package com.azo.backend.msvc.users_prod.msvc_users_prod.models;

import java.util.Properties;

import org.eclipse.angus.mail.smtp.SMTPTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azo.backend.msvc.users_prod.msvc_users_prod.config.GmailProperties;
import com.google.auth.oauth2.OAuth2Credentials;
import com.google.auth.oauth2.UserCredentials;

import jakarta.mail.Session;

@Component
public class GmailOAuth2Provider {

  private final GmailProperties gmailProperties;

  @Autowired
  public GmailOAuth2Provider(GmailProperties gmailProperties) {
      this.gmailProperties = gmailProperties;
  }

  @Value("${gmail.client.id}")
  private String clientId;

  @Value("${gmail.client.secret}")
  private String clientSecret;

  @Value("${gmail.refresh.token}")
  private String refreshToken;

  @Value("${gmail.email}")
  private String email;

  public Session getSession() {
      Properties props = new Properties();
      props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
      props.put("mail.smtp.auth.login.disable", "true");
      props.put("mail.smtp.auth.plain.disable", "true");
      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
      props.put("mail.smtp.ssl.protocols", "TLSv1.2");

      return Session.getInstance(props);
  }

  public SMTPTransport getSMTPTransport(Session session) throws Exception {
    SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");

    OAuth2Credentials credentials = UserCredentials.newBuilder()
            .setClientId(gmailProperties.getClientId())
            .setClientSecret(gmailProperties.getClientSecret())
            .setRefreshToken(gmailProperties.getRefreshToken())
            .build();

    String accessToken = credentials.refreshAccessToken().getTokenValue();

    transport.connect("smtp.gmail.com", 587, gmailProperties.getEmail(), accessToken);
    return transport;
  }
  
}
