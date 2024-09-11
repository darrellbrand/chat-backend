package com.djf.chat_boss.chat;

import io.getstream.chat.java.models.Channel;
import io.getstream.chat.java.models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public String getToken(String name) {

        String token = null;
        try {
            var AdminUser =
                    User.UserRequestObject.builder()
                            .name("BACKEND-ADMIN")
                            .id("BACKEND-ADMIN")
                            .build();

            Channel.getOrCreate("messaging", "my-team-channel")
                    .data(
                            Channel.ChannelRequestObject.builder()
                                    .createdBy(AdminUser)
                                    .build())
                    .request();
            Channel.update("messaging", "my-team-channel")
                    .data(Channel.ChannelRequestObject.builder()
                            .additionalField("name", "my-team-channel")
                            .additionalField("color", "green")
                            .build())
                    .request();
            token = User.createToken(name, null, null);
            var user = User.UserRequestObject.builder().id(name).role("admin").build();
            User.upsert().user(user).request();
            Channel.update("messaging", "my-team-channel").addMember(name).hideHistory(true).request();
            // Channel.update("messaging", "my-team-channel").removeMember(name).hideHistory(true).request();
            //  Channel.delete("messaging", "my-team-channel");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return token;
    }
}
