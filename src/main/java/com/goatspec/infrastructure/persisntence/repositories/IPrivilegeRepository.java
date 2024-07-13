package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entities.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPrivilegeRepository extends JpaRepository<PrivilegeEntity, UUID> {
    Optional<PrivilegeEntity> findByNameAndActive(String name, boolean active);
}
