package com.djf.chat_boss.security;

import io.getstream.chat.java.models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {
    @Value("${io.getstream.chat.apiKey}")
    private String apiKey;
    @Value("${io.getstream.chat.apiSecret}")
    private String apiSecret;
  @PostConstruct
  public void init() {
      System.out.println("apiKey: " + apiKey);
      System.out.println("apiSecret: " + apiSecret);
      System.setProperty("io.getstream.chat.apiKey", apiKey);
      System.setProperty("io.getstream.chat.apiSecret", apiSecret);
  }
    public String getToken(String name){
        return User.createToken(name,null,null);
    }
}
