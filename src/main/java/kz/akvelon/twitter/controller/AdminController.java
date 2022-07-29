package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping("/ban")
    public ResponseEntity<?> subscribe(@RequestBody String email){
        if(Objects.equals(email, userService.isLogged())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't ban yourself");
        }
        userService.ban(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("You have successfully banned");
    }

}
