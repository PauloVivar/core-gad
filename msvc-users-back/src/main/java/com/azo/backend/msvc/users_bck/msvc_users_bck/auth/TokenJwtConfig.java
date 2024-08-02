package com.azo.backend.msvc.users_bck.msvc_users_bck.auth;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;


public class TokenJwtConfig {
  //public final static String SECRET_KEY = "algun_texto_secreto_random";
  public final static SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
  public final static String PREFIX_TOKEN = "Bearer ";
  public final static String HEADER_AUTHORIZATION = "Authorization";

}
