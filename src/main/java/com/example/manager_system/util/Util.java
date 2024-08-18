package com.example.manager_system.util;

import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class Util {

    public String decoder(String base64Header)
    {
        String encodedString = base64Header.substring("Basic ".length());

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public String retrieveUserId(String base64Header)
    {
        String regex = "\"userId\"\\s*:\\s*(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(base64Header);

        if (matcher.find()) {
            String userIdValue = matcher.group(1);
//            System.out.println("UserId value: " + userIdValue);
            return userIdValue;
        } else {
            System.out.println("No match found.");
            return null;
        }
    }

    public String retrieveUserRole(String base64Header)
    {
        String regex = "\"role\"\\s*:\\s*\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(base64Header);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("No match found.");
            return null;
        }
    }
}
