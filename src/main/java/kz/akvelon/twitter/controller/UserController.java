package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.request.UserUpdateDto;
import kz.akvelon.twitter.dto.response.users.UserDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<?> profile() {
        Account account = userService.findByEmail(userService.isLogged());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Register first");
        }

        return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(account));
    }

    @DeleteMapping()
    public ResponseEntity<?> delete() {
        Account account = userService.findByEmail(userService.isLogged());

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }

        userService.delete(account.getId());
        return new ResponseEntity<>("User successfully deleted", HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody UserUpdateDto userUpdateDto) {
        Account account = userService.findByEmail(userService.isLogged());

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }

        Account updateAccount = new Account(account.getId(), account.getEmail(), userUpdateDto.getUsername());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(updateAccount));
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long id){
        if(userService.subscribe(id, userService.isLogged())){
            return ResponseEntity.status(HttpStatus.OK).body("You have successfully subscribed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("You have already subscribed");
    }

}
