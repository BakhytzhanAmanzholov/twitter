package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.AdminApi;
import kz.akvelon.twitter.dto.request.RoleUserDto;
import kz.akvelon.twitter.model.BlackList;
import kz.akvelon.twitter.service.AdminService;
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

    private final AdminService adminService;

    @PostMapping("/ban")
    @Override
    public ResponseEntity<?> ban(@RequestBody String email) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.ban(email));
    }

    @Override
    public ResponseEntity<?> addWordToBlackList(@RequestBody String word) {
        adminService.save(BlackList.builder()
                .word(word)
                .build());

        return ResponseEntity.status(HttpStatus.OK).body(word + " successfully added to the blacklist");
    }

    @Override
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserDto dto) {
        adminService.addRoleToUser(dto.getEmail(), dto.getRoleName());
        return ResponseEntity.status(HttpStatus.OK).body("Role successfully added to User");
    }

}
