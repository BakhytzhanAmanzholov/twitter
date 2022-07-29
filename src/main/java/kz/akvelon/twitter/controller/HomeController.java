package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.request.RegistrationDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.service.FeedService;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    private final TweetService tweetService;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto) {
        try {
            userService.findByEmail(registrationDto.getEmail());
        }
        catch (UsernameNotFoundException e){
            Account account = Account.fromRequestDto(registrationDto);
            userService.save(account);
            return new ResponseEntity<>("Please confirm your email", HttpStatus.OK);
        }

        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        /*
        if(userService.findByEmail(registrationDto.getEmail()) != null){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        Account account = Account.fromRequestDto(registrationDto);
        userService.save(account);
        return new ResponseEntity<>("Please confirm your email", HttpStatus.OK);
         */
    }
    @GetMapping("/confirmEmail/{email}/{username}")
    public ResponseEntity<?> confirm(@PathVariable("email") String email, @PathVariable("username") String username) {
        userService.confirm(email, username);
        return new ResponseEntity<>("User successfully registered ", HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> index(@PageableDefault() Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.getTweets(pageable));
    }
}
