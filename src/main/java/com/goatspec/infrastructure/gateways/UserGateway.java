package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserGateway implements IUserGateway {
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;

    public UserGateway(IUserRepository userRepository, UserEntityMapper userEntityMapper, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = this.userEntityMapper.toUserEntity(user);
        RoleEntity roleEntity = this.roleRepository.findByNameAndActive(user.role(), true);
        userEntity.setActive(true);
        userEntity.setRoles(Collections.singletonList(roleEntity));
        userEntity = this.userRepository.save(userEntity);
        return this.userEntityMapper.toUserDomainObject(userEntity, roleEntity);
    }

    @Override
    public User findUserByCpf(String cpf) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByCpfAndActive(cpf, true);
        if (userEntityResult.isPresent()) {
            List<RoleEntity> roles = userEntityResult.get().getRoles().stream().toList();
            return this.userEntityMapper.toUserDomainObject(userEntityResult.get(), roles.getLast());
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByEmailAndActive(email, true);
        if (userEntityResult.isPresent()) {
            List<RoleEntity> roles = userEntityResult.get().getRoles().stream().toList();
            return this.userEntityMapper.toUserDomainObject(userEntityResult.get(), roles.getLast());
        }
        return null;
    }

    @Override
    public User findUserByRegistration(String registration) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByRegistrationAndActive(registration, true);
        if (userEntityResult.isEmpty()) return null;
        return null;
    }
}
