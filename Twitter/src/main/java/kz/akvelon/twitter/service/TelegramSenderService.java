package kz.akvelon.twitter.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface TelegramSenderService {
    void sendMessage(String number, String message) throws IOException;
}
