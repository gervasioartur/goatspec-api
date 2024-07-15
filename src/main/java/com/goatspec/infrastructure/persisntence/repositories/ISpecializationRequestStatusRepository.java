package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISpecializationRequestStatusRepository extends JpaRepository<SpecializationRequestStatusEntity, UUID> {
    SpecializationRequestStatusEntity findByDescriptionAndActive(String description, boolean active);
}
