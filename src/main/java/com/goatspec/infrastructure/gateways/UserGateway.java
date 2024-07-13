package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;

import java.util.Arrays;
import java.util.Collections;

public class UserGateway implements IUserGateway {
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private  final IRoleRepository roleRepository;

    public UserGateway(IUserRepository userRepository, UserEntityMapper userEntityMapper, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userEntityMapper =  userEntityMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = this.userEntityMapper.toUserEntity(user);
        RoleEntity roleEntity =  this.roleRepository.findByName(user.role());
        userEntity.setRoles(Collections.singletonList(roleEntity));
        userEntity = this.userRepository.save(userEntity);
        return this.userEntityMapper.toUserDomainObject(userEntity,roleEntity);
    }

    @Override
    public User findUserByCpf(String cpf) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByRegistration(String registration) {
        return null;
    }
}
