package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
}
