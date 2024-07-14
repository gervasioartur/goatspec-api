package com.goatspec.infrastructure.gateways.user;

import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class LoadUserByUsernameGateway implements UserDetailsService {
    private final IUserRepository userRepository;

    public LoadUserByUsernameGateway(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntity> userEntityResult = this.userRepository.findByCpfAndActive(username, true);
        return userEntityResult.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
