package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.UserUpdateDto;
import kz.akvelon.twitter.model.Account;
import kz.akvelon.twitter.service.EmailSenderService;
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

    private String isLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info(currentPrincipalName);
        if (!currentPrincipalName.equals("anonymousUser")) {
            return currentPrincipalName;
        }
        return "anonymousUser";
    }

    @GetMapping()
    public ResponseEntity<?> profile() {
        Account account = userService.findByEmail(isLogged());

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Register first");
        }

        return ResponseEntity.ok(account);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete() {
        Account account = userService.findByEmail(isLogged());

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }

        userService.delete(account);

        return new ResponseEntity<>("User successfully deleted", HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody UserUpdateDto userUpdateDto) {
        Account account = userService.findByEmail(isLogged());

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }

        Account updateAccount = new Account(account.getId(), account.getEmail(), userUpdateDto.getUsername());
        return ResponseEntity.ok(userService.update(updateAccount));
    }
}
