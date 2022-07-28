package kz.akvelon.mqemail.service.implementation;


import kz.akvelon.mqemail.service.EmailService;
import kz.akvelon.mqemail.util.GenerateLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final GenerateLink generateLink;

    @Override
    public void sendEmail(String email, String username) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.mail.ru");
            props.put("mail.smtp.port", "465");
//            props.put("mail.debug", "true");
            props.put("mail.smtp.ssl.enable", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("twitter.bakhayeldos@mail.ru",
                            "QFMXnz003EwARksx1UKU");
                }
            });

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.mail.ru", "twitter.bakhayeldos@mail.ru",
                    "QFMXnz003EwARksx1UKU");
            Message message = new MimeMessage(session);
            message.setSubject("Please verify you email");
            Address[] address = {new InternetAddress("twitter.bakhayeldos@mail.ru")};
            message.addFrom(address);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSentDate(new Date());

            String[] args = new String[]{username, "Twitter", email, generateLink.generateLink(email, username)};
            String content = readTemplate("src/main/resources/templates/email.html");
            for (int i = 0; i < args.length; i++) {
                content = content.replace("{" + i + "}", args[i]);
            }
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setDataHandler(new DataHandler(content, "text/html; charset=gbk"));
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);
            message.saveChanges();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    private String readTemplate(String templatePath) {
        if (!new File(templatePath).exists()) {
            return "";
        }
        try {
            InputStream input = new FileInputStream(templatePath);
            InputStreamReader read = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(read);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
            read.close();
            input.close();
            result = new StringBuilder(result.substring(result.indexOf("<html>")));
            System.out.println(result);
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
