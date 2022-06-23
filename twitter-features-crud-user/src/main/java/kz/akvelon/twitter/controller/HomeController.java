package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.LoginDto;
import kz.akvelon.twitter.dto.RegistrationDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.model.AccountTemp;
import kz.akvelon.twitter.service.AccountTempService;
import kz.akvelon.twitter.service.EmailSenderService;
import kz.akvelon.twitter.service.UserService;
import kz.akvelon.twitter.token.JWTAuthResponse;
import kz.akvelon.twitter.token.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil tokenProvider;
    private final AccountTempService accountTempService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Account person = userService.findByEmail(loginDto.getEmail());
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new JWTAuthResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto) {
        if (userService.findByEmail(registrationDto.getEmail()) != null) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (accountTempService.findByEmail(registrationDto.getEmail()) != null) {
            return new ResponseEntity<>("Please confirm your email", HttpStatus.BAD_REQUEST);
        }
        AccountTemp account = new AccountTemp();
        account.setEmail(registrationDto.getEmail());
        account.setPassword(registrationDto.getPassword());
        account.setUsername(registrationDto.getUsername());
        accountTempService.save(account);
        return new ResponseEntity<>("Please confirm your email", HttpStatus.OK);
    }

    @GetMapping("/confirmEmail/{email}/{username}")
    public ResponseEntity<?> confirm(@PathVariable("email") String email, @PathVariable("username") String username) {
        AccountTemp accountTemp = accountTempService.findByEmail(email);
        Account account = new Account(accountTemp.getEmail(), accountTemp.getUsername(), accountTemp.getPassword());
        userService.save(account);
        accountTempService.delete(accountTemp);
        return new ResponseEntity<>("User successfully registered ", HttpStatus.OK);
    }
}
