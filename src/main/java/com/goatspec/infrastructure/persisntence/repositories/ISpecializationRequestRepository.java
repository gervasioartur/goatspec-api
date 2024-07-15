package com.goatspec.infrastructure.persisntence.repositories;

import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISpecializationRequestRepository extends JpaRepository<SpecializationRequestEntity, UUID> {
    Optional<SpecializationRequestEntity> findByIdAndActive(UUID id, Boolean active);

    List<SpecializationRequestEntity> findAllByActive(Boolean active);

    @Query("SELECT spec FROM SpecializationRequestEntity spec WHERE spec.user.id = :userId and spec.specializationRequestStatus.active = true ")
    List<SpecializationRequestEntity> findByUserId(@Param("userId") UUID userId);
}
