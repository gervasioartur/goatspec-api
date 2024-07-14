package com.goatspec.infrastructure.gateways.authetication;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.infrastructure.gateways.helpers.token.GenerateToken;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthenticationGateway implements IAuthenticationGateway {
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public AuthenticationGateway(AuthenticationManager authenticationManager, GenerateToken generateToken, IUserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public UserInfo getLoggedUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> userEntity = this.userRepository.findByCpfAndActive(authentication.getName(), true);
        return userEntity.map(this.userEntityMapper::toUserInfo).get();
    }

    @Override
    public String authenticate(String username, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return this.generateToken.generate(username);
    }
}
