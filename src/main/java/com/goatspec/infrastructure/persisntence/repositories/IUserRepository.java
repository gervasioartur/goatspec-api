package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByCpfAndActive(String cpf, boolean active);
    Optional<UserEntity> findByEmailAndActive(String email, boolean active);
}
