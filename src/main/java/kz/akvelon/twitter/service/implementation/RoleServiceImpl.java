package kz.akvelon.twitter.service.implementation;

import kz.akvelon.twitter.model.Role;
import kz.akvelon.twitter.repository.RoleRepository;
import kz.akvelon.twitter.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role entity) {
        log.info("Saving new role by name {}", entity.getName());
        return roleRepository.save(entity);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
