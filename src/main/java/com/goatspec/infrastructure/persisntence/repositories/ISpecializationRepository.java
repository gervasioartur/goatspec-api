package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISpecializationRepository extends JpaRepository<SpecializationEntity, UUID> {
}
