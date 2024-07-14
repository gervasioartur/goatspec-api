package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entities.SpecializationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISpecializationStatusRepository extends JpaRepository<SpecializationStatusEntity, UUID> {
    SpecializationStatusEntity findByDescriptionAndActive(String description, boolean active);
}
