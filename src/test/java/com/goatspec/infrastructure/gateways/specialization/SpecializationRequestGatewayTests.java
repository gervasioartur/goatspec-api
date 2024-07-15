package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.domain.Enums.SpecializationRequestStatusEnum;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import com.goatspec.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class SpecializationRequestGatewayTests {
    private ISpecializationRequestGateway specializationGateway;

    @MockBean
    private ISpecializationRequestStatusRepository specializationSituationRepository;
    @MockBean
    private ISpecializationRequestRepository specializationRepository;
    @MockBean
    private SpecializationEntityMapper specializationEntityMapper;
    @MockBean
    private IUserRepository userRepository;


    @BeforeEach
    void setUp() {
        this.specializationGateway = new SpecializationRequestGateway
                (specializationSituationRepository,
                        specializationRepository,
                        specializationEntityMapper,
                        userRepository);
    }

    @Test
    @DisplayName("Should return specialization domain object")
    void shouldReturnSpecializationDomainObject() {
        UUID userId = UUID.randomUUID();
        SpecializationRequest specializationRequestDomainObject = new SpecializationRequest
                (userId, "any_area", "any_type", 2, new BigDecimal("25"));

        SpecializationRequestEntity toCreateSpecializationRequestEntity = SpecializationRequestEntity
                .builder()
                .user(UserEntity.builder().id(specializationRequestDomainObject.userId()).build())
                .area(specializationRequestDomainObject.area())
                .type(specializationRequestDomainObject.type())
                .courseLoad(specializationRequestDomainObject.courseLoad())
                .totalCost(specializationRequestDomainObject.totalCost())
                .build();

        SpecializationRequestStatusEntity savedSpecializationRequestStatusEntity = SpecializationRequestStatusEntity
                .builder()
                .id(UUID.randomUUID())
                .description(SpecializationRequestStatusEnum.PENDING.getValue())
                .active(true)
                .build();

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();

        UserEntity savedUserEntity = UserEntity
                .builder()
                .id(userId)
                .cpf("any_cpf")
                .email("any_email")
                .registration("any_registration")
                .name("any_name")
                .dateOfBirth(new Date())
                .gender("any_gender")
                .password("any_password")
                .roles(Collections.singletonList(savedRoleEntity))
                .active(true)
                .build();

        Mockito.when(this.specializationEntityMapper.toSpecializationEntity(specializationRequestDomainObject)).thenReturn(toCreateSpecializationRequestEntity);
        Mockito.when(this.specializationSituationRepository
                .findByDescriptionAndActive(SpecializationRequestStatusEnum.PENDING.getValue(), true)).thenReturn(savedSpecializationRequestStatusEntity);

        Mockito.when(this.userRepository.findByIdAndActive(userId, true)).thenReturn(Optional.of(savedUserEntity));

        toCreateSpecializationRequestEntity.setActive(true);
        toCreateSpecializationRequestEntity.setUser(savedUserEntity);
        toCreateSpecializationRequestEntity.setSpecializationRequestStatus(savedSpecializationRequestStatusEntity);


        SpecializationRequestEntity savedCreateSpecializationRequestEntity = SpecializationRequestEntity
                .builder()
                .id(UUID.randomUUID())
                .user(savedUserEntity)
                .area(specializationRequestDomainObject.area())
                .type(specializationRequestDomainObject.type())
                .courseLoad(specializationRequestDomainObject.courseLoad())
                .totalCost(specializationRequestDomainObject.totalCost())
                .specializationRequestStatus(savedSpecializationRequestStatusEntity)
                .active(true)
                .build();

        Mockito.when(this.specializationRepository.save(toCreateSpecializationRequestEntity)).thenReturn(savedCreateSpecializationRequestEntity);
        Mockito.when(this.specializationEntityMapper.toSpecializationDomainObject(savedCreateSpecializationRequestEntity)).thenReturn(specializationRequestDomainObject);


        SpecializationRequest result = this.specializationGateway.create(specializationRequestDomainObject);

        Assertions.assertThat(result).isEqualTo(specializationRequestDomainObject);

        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationEntity(specializationRequestDomainObject);
        Mockito.verify(this.specializationSituationRepository, Mockito.times(1)).findByDescriptionAndActive(SpecializationRequestStatusEnum.PENDING.getValue(), true);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdAndActive(userId, true);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).save(toCreateSpecializationRequestEntity);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationDomainObject(savedCreateSpecializationRequestEntity);
    }

    @Test
    @DisplayName("Should list of SpecializationAndUser ")
    void shouldListOfSpecializationAndUser() {
        List<SpecializationRequestEntity> specializationRequestEntityList = new ArrayList<>(Arrays.asList(Mocks.specializationEntityFactory(), Mocks.specializationEntityFactory()));
        List<SpecializationRequestInfo> specializationRequestInfoList = new ArrayList<>(Arrays.asList(Mocks.SpecializationRequestInfoFactory(), Mocks.SpecializationRequestInfoFactory()));

        Mockito.when(this.specializationRepository.findAll()).thenReturn(specializationRequestEntityList);
        Mockito.when(this.specializationEntityMapper.toSpecializationInfoList(specializationRequestEntityList)).thenReturn(specializationRequestInfoList);

        List<SpecializationRequestInfo> result = this.specializationGateway.getAll();

        Assertions.assertThat(result).isEqualTo(specializationRequestInfoList);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findAll();
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationInfoList(specializationRequestEntityList);
    }

    @Test
    @DisplayName("should return null if the specialization does not exists")
    void ShouldReturnNullIfTheSpecializationDoesNotExists() {
        UUID specializationId = UUID.randomUUID();
        Mockito.when(this.specializationRepository.findByIdAndActive(specializationId, true)).thenReturn(Optional.empty());
        SpecializationRequest result = this.specializationGateway.findById(specializationId);
        Assertions.assertThat(result).isNull();
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findByIdAndActive(specializationId, true);
    }

    @Test
    @DisplayName("Should return specialization domain object")
    void shouldReturnSpecializationDomainObjectById() {
        SpecializationRequestEntity savedSpecializationRequestEntity = Mocks.specializationEntityFactory();
        SpecializationRequest specializationRequestObjectDomain = Mocks.specializationDomainObjectFactory(savedSpecializationRequestEntity);

        Mockito.when(this.specializationRepository.findByIdAndActive(savedSpecializationRequestEntity.getId(), true)).thenReturn(Optional.of(savedSpecializationRequestEntity));
        Mockito.when(this.specializationEntityMapper.toSpecializationDomainObject(savedSpecializationRequestEntity)).thenReturn(specializationRequestObjectDomain);

        SpecializationRequest result = this.specializationGateway.findById(savedSpecializationRequestEntity.getId());

        Assertions.assertThat(result).isEqualTo(specializationRequestObjectDomain);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findByIdAndActive(savedSpecializationRequestEntity.getId(), true);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationDomainObject(savedSpecializationRequestEntity);
    }

    @Test
    @DisplayName("Should throw npt fund exception if the specialization does not exists")
    void shouldThrowNptFundExceptionIfTheSpecializationDoesNotExists() {
        UUID specializationId = UUID.randomUUID();
        Mockito.when(this.specializationRepository.findByIdAndActive(specializationId, true)).thenReturn(Optional.empty());

        Throwable exception = Assertions.catchThrowable(() -> this.specializationGateway.approve(specializationId));
        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Specialization not found.");
    }

    @Test
    @DisplayName("Should approve specialization request")
    void shouldApproveSpecializationRequest() {
        SpecializationRequestEntity specializationRequestEntity = Mocks.specializationEntityFactory();
        SpecializationRequestStatusEntity specializationRequestStatusEntity = Mocks.specializationStatusEntityFactory();
        specializationRequestStatusEntity.setDescription("APPROVED");

        Mockito.when(this.specializationRepository.findByIdAndActive(specializationRequestEntity.getId(), true)).thenReturn(Optional.of(specializationRequestEntity));
        Mockito.when(this.specializationSituationRepository.findByDescriptionAndActive(SpecializationRequestStatusEnum.APPROVED.getValue(), true)).thenReturn(specializationRequestStatusEntity);

        specializationRequestEntity.setSpecializationRequestStatus(specializationRequestStatusEntity);
        Mockito.when(this.specializationRepository.save(specializationRequestEntity)).thenReturn(specializationRequestEntity);

        SpecializationRequestInfo specializationRequestInfo = Mocks.specializationInfoFactory(specializationRequestEntity);
        Mockito.when(this.specializationEntityMapper.toSpecializationInfo(specializationRequestEntity)).thenReturn(specializationRequestInfo);

        SpecializationRequestInfo result = this.specializationGateway.approve(specializationRequestEntity.getId());

        Assertions.assertThat(result).isEqualTo(specializationRequestInfo);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findByIdAndActive(specializationRequestEntity.getId(), true);
        Mockito.verify(this.specializationSituationRepository, Mockito.times(1)).findByDescriptionAndActive(SpecializationRequestStatusEnum.APPROVED.getValue(), true);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).save(specializationRequestEntity);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationInfo(specializationRequestEntity);
    }

}
