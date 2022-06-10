package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.service.RoleService;
import kz.akvelon.twitter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody String name) {
        if (roleService.findByName(name) != null) {
            return new ResponseEntity<>("Role is already created!", HttpStatus.BAD_REQUEST);
        }
        Role role = new Role(name);
        roleService.save(role);
        return new ResponseEntity<>("Role create successfully", HttpStatus.OK);
    }
}
