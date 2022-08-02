package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.AdminApi;
import kz.akvelon.twitter.dto.request.RoleUserDto;
import kz.akvelon.twitter.model.BlackList;
import kz.akvelon.twitter.service.BlackListService;
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
public class AdminController implements AdminApi {

    private final UserService userService;

    private final BlackListService blackListService;

    @PostMapping("/ban")
    @Override
    public ResponseEntity<?> ban(@RequestBody String email) {
        if (Objects.equals(email, userService.isLogged())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't ban yourself");
        }
        userService.ban(email);

        return ResponseEntity.status(HttpStatus.OK).body("You have successfully banned");
    }

    @Override
    public ResponseEntity<?> addWordToBlackList(@RequestBody String word) {
        blackListService.save(BlackList.builder()
                .word(word)
                .build());

        return ResponseEntity.status(HttpStatus.OK).body("Word successfully added");
    }

    @Override
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserDto dto) {
        userService.addRoleToUser(dto.getEmail(), dto.getRoleName());

        return ResponseEntity.status(HttpStatus.OK).body("Role successfully added to User");
    }


}
