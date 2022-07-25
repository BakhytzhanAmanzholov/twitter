package kz.akvelon.twitter.util;

import org.springframework.stereotype.Service;

@Service
public class GenerateLinkImpl implements GenerateLink{
    @Override
    public String generateLink(String email, String username) {
        return "http://localhost:8181/confirmEmail/" + email + "/" +
                username;
    }
}
