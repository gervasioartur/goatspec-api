package com.goatspec.main.config;

import com.goatspec.infrastructure.persisntence.entities.PrivilegeEntity;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.repositories.IPrivilegeRepository;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class SetupLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPrivilegeRepository privilegeRepository;

    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        PrivilegeEntity deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_TEACHER", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_TECHNICIAN", Arrays.asList(readPrivilege, writePrivilege));

        alreadySetup = true;
    }

    PrivilegeEntity createPrivilegeIfNotFound(String name) {
        Optional<PrivilegeEntity> privilegeResult = privilegeRepository.findByNameAndActive(name, true);
        PrivilegeEntity privilege = null;
        if (privilegeResult.isEmpty()) {
            privilege = PrivilegeEntity.builder().name(name).active(true).build();
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {
        RoleEntity roleResult = roleRepository.findByNameAndActive(name, true);
        RoleEntity role = null;
        if (roleResult == null) {
            role = RoleEntity.builder().name(name).active(true).build();
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
