package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.service.EmailSenderService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/email")
    public String sendEmail(@RequestBody String email) {
        //yeldos.manap@gmail.com
        //amanzholovbakhytzhan@gmail.com
        emailSenderService.sendEmail(email, "Bakhytzhan");
        return "Email sent successfully";
    }
}
