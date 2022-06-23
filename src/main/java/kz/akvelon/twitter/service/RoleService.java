package kz.akvelon.twitter.service;

import kz.akvelon.twitter.model.Role;

public interface RoleService extends CrudService<Role, Long>{
    Role findByName(String name);
}
