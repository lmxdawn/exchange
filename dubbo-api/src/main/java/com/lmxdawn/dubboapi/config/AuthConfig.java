package com.lmxdawn.dubboapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dubboapi")
public class AuthConfig {
    private String authKey = "xxx";

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
