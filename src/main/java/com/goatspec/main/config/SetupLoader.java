package com.goatspec.main.config;

import com.goatspec.application.gateways.encrypt.IPasswordEncoderGateway;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.infrastructure.persisntence.entities.PrivilegeEntity;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IPrivilegeRepository;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Transactional
public class SetupLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Value("${spring.profiles.active}")
    private String profile;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPrivilegeRepository privilegeRepository;
    @Autowired
    private ISpecializationRequestStatusRepository specializationStatusRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPasswordEncoderGateway passwordEncoderGateway;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        if (profile.equalsIgnoreCase("test")) return;

        PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        PrivilegeEntity deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege);
        createRoleIfNotFound("ROLE_TEACHER", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_TECHNICIAN", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        createAdminUserIfNotFound();

        createSpecializationSituationIfNotFound("PENDING");
        createSpecializationSituationIfNotFound("APPROVED");
        createSpecializationSituationIfNotFound("DISAPPROVED");


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

    void createAdminUserIfNotFound(){
        if (userRepository.findByNameAndActive("admin", true).isEmpty()) {
            RoleEntity adminRole = roleRepository.findByNameAndActive("ROLE_ADMIN", true);
            String password = passwordEncoderGateway.encode("admin");
            UserEntity user = UserEntity
                    .builder()
                    .cpf("admin")
                    .email("gervasioarthur@gmailcom")
                    .registration("admin")
                    .name("admin")
                    .dateOfBirth(new Date())
                    .gender(GenderEnum.MALE.getValue())
                    .roles(List.of(adminRole))
                    .password(password)
                    .active(true)
                    .build();
            userRepository.save(user);
        }
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

    void createSpecializationSituationIfNotFound(String description) {
        SpecializationRequestStatusEntity specializationRequestStatusEntityResult = this.specializationStatusRepository
                .findByDescriptionAndActive(description, true);
        if (specializationRequestStatusEntityResult == null) {
            SpecializationRequestStatusEntity specializationRequestStatusEntity = SpecializationRequestStatusEntity.builder()
                    .description(description)
                    .active(true)
                    .build();
            specializationStatusRepository.save(specializationRequestStatusEntity);
        }
    }
}
