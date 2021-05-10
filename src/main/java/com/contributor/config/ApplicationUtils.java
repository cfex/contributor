package com.contributor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
@PropertySources({@PropertySource("classpath:utils.yml")})
public class ApplicationUtils {

    @Value("${length}")
    private int defaultLength; //30

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghjklmnopqrstuvwxyz";

    public String generateUUID() {
        return generate(defaultLength);
    }

    private String generate(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }
}
