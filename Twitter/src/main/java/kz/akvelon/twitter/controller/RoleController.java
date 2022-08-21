package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.RoleApi;
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
public class RoleController implements RoleApi {
    private final RoleService roleService;

    @PostMapping
    @Override
    public ResponseEntity<?> save(@RequestBody String name) {
        if (roleService.findByName(name) != null) {
            return ResponseEntity.badRequest().body("Role is already created!");
        }
        return new ResponseEntity<>(roleService.save(new Role(name)), HttpStatus.OK);
    }
}
