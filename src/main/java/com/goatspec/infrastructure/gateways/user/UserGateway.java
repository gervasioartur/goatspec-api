package com.goatspec.infrastructure.gateways.user;

import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.domain.entities.user.User;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Transactional
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
        return this.userEntityMapper.toDomainObject(userEntity, roleEntity);
    }

    @Override
    public User findUserById(UUID id) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByIdAndActive(id, true);
        return userEntityResult.map(entity -> this.userEntityMapper.toDomainObject(entity, entity.getRoles().stream().toList().getLast())).orElse(null);
    }

    @Override
    public User findUserByCpf(String cpf) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByCpfAndActive(cpf, true);
        return userEntityResult.map(entity -> this.userEntityMapper.toDomainObject(entity, entity.getRoles().stream().toList().getLast())).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByEmailAndActive(email, true);
        return userEntityResult.map(entity -> this.userEntityMapper.toDomainObject(entity, entity.getRoles().stream().toList().getLast())).orElse(null);
    }

    @Override
    public User findUserByRegistration(String registration) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByRegistrationAndActive(registration, true);
        return userEntityResult.map(entity -> this.userEntityMapper.toDomainObject(entity, entity.getRoles().stream().toList().getLast())).orElse(null);
    }
}
