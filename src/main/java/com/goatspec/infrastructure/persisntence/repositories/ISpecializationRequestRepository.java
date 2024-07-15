package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISpecializationRequestRepository extends JpaRepository<SpecializationRequestEntity, UUID> {
    Optional<SpecializationRequestEntity> findByIdAndActive(UUID id, Boolean active);
}
