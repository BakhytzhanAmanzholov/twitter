package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.RegistrationApi;
import kz.akvelon.twitter.dto.request.PageDto;
import kz.akvelon.twitter.dto.request.RegistrationDto;
import kz.akvelon.twitter.dto.request.RoleUserDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.service.FeedService;
import kz.akvelon.twitter.service.TweetService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
public class HomeController implements RegistrationApi {
    private final UserService userService;


    @Override
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
    }

    @Override
    public ResponseEntity<?> confirmEmail(@PathVariable("email") String email, @PathVariable("username") String username) {
        userService.confirm(email, username);
        return new ResponseEntity<>("User successfully registered ", HttpStatus.OK);
    }

}
