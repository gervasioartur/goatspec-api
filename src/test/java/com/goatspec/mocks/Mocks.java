package com.goatspec.mocks;

import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Mocks {
    public static SpecializationRequestEntity specializationEntityFactory() {
        return SpecializationRequestEntity
                .builder()
                .id(UUID.randomUUID())
                .user(userFactory())
                .area("any_")
                .type("any_")
                .courseLoad(200)
                .totalCost(new BigDecimal(30))
                .specializationRequestStatus(specializationStatusEntityFactory())
                .active(true)
                .build();
    }

    public static UserEntity userFactory() {
        return UserEntity
                .builder()
                .id(UUID.randomUUID())
                .cpf("any_cpf")
                .email("any_email")
                .registration("any_registration")
                .name("any_name")
                .dateOfBirth(new Date())
                .gender("any_gender")
                .password("any_password")
                .roles(Collections.singletonList(roleFactory()))
                .active(true)
                .build();
    }

    public static RoleEntity roleFactory() {
        return RoleEntity.builder()
                .name("any_name")
                .active(true)
                .build();
    }

    public static SpecializationRequestStatusEntity specializationStatusEntityFactory() {
        return SpecializationRequestStatusEntity
                .builder()
                .id(UUID.randomUUID())
                .description("any_description")
                .active(true)
                .build();
    }

    public static SpecializationRequest specializationDomainObjectFactory() {
        return new SpecializationRequest(UUID.randomUUID(), "any_area", "any_type", 200, new BigDecimal(20));
    }

    public static SpecializationRequest specializationDomainObjectFactory(UUID userId) {
        return new SpecializationRequest(userId, "any_area", "any_type", 200, new BigDecimal(20));
    }

    public static SpecializationRequest specializationDomainObjectFactory(SpecializationRequestEntity specializationRequestEntity) {
        return new SpecializationRequest
                (specializationRequestEntity.getUser().getId(), specializationRequestEntity.getArea(), specializationRequestEntity.getType(), specializationRequestEntity.getCourseLoad(), specializationRequestEntity.getTotalCost());
    }

    public static SendEmailParams sendApprovedEmailParamsFactory(SpecializationRequestInfo result) {
        return new SendEmailParams(result.userEmail(),
                "Feedback on Specialization Request", "Congratulations! " + result.userName() + "\n" +
                "Your specialization request for " + result.type() + " on area " + result.area() + " has been successfully approved.");
    }

    public static SendEmailParams sendDisapprovedEmailParamsFactory(SpecializationRequestInfo result) {
        return new SendEmailParams(result.userEmail(),
                "Feedback on Specialization Request", "We sorry! " + result.userName() + "\n" +
                "Your specialization request for " + result.type() + " on area " + result.area() + " has been  disapproved.");
    }

    public static UserInfo userInfoFactory() {
        return new UserInfo(UUID.randomUUID(), "any_name", "any_email", "any_registration");
    }

    public static List<SpecializationRequestInfo> specializationInfoListFactory(List<SpecializationRequestEntity> specializationEntities) {
        return specializationEntities.stream()
                .map(Mocks::specializationInfoFactory).collect(Collectors.toList());
    }

    public static SpecializationRequestInfo specializationInfoFactory(String status) {
        return new SpecializationRequestInfo(
                "any_username",
                "any_user_email",
                "any_registration",
                "specialization_request_id",
                "any_area",
                "any_type",
                200,
                new BigDecimal(20),
                status);
    }


    public static SpecializationRequestInfo specializationInfoFactory(SpecializationRequestEntity specializationEntity) {
        return new SpecializationRequestInfo(
                specializationEntity.getUser().getName(),
                specializationEntity.getUser().getEmail(),
                specializationEntity.getUser().getRegistration(),
                specializationEntity.getId().toString(),
                specializationEntity.getArea(),
                specializationEntity.getType(),
                specializationEntity.getCourseLoad(),
                specializationEntity.getTotalCost(),
                specializationEntity.getSpecializationRequestStatus().getDescription());
    }


    public static SpecializationRequestInfo SpecializationRequestInfoFactory() {
        return new SpecializationRequestInfo(
                "any_username",
                "any_user_email",
                "any_registration",
                "specialization_request_id",
                "any_area",
                "any_type",
                200,
                new BigDecimal(20),
                "PENDING");
    }
}
