package kz.akvelon.mqemail.util;

import org.springframework.stereotype.Service;

@Service
public class GenerateLinkImpl implements GenerateLink{

    @Override
    public String generateLink(String email, String username) {
        return "http://localhost/confirmEmail/" + email + "/" +
                username;
    }
}
