package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.domain.Enums.SpeciaiizationStatusEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationStatusRepository;
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
class SpecializationGatewayTests {
    private ISpecializationGateway specializationGateway;

    @MockBean
    private ISpecializationStatusRepository specializationSituationRepository;
    @MockBean
    private ISpecializationRepository specializationRepository;
    @MockBean
    private SpecializationEntityMapper specializationEntityMapper;
    @MockBean
    private IUserRepository userRepository;


    @BeforeEach
    void setUp() {
        this.specializationGateway = new SpecializationGateway
                (specializationSituationRepository,
                        specializationRepository,
                        specializationEntityMapper,
                        userRepository);
    }

    @Test
    @DisplayName("Should return specialization domain object")
    void shouldReturnSpecializationDomainObject() {
        UUID userId = UUID.randomUUID();
        Specialization specializationDomainObject = new Specialization
                (userId, "any_area", "any_type", 2, new BigDecimal("25"));

        SpecializationEntity toCreateSpecializationEntity = SpecializationEntity
                .builder()
                .user(UserEntity.builder().id(specializationDomainObject.userId()).build())
                .area(specializationDomainObject.area())
                .type(specializationDomainObject.type())
                .courseLoad(specializationDomainObject.courseLoad())
                .totalCost(specializationDomainObject.totalCost())
                .build();

        SpecializationStatusEntity savedSpecializationStatusEntity = SpecializationStatusEntity
                .builder()
                .id(UUID.randomUUID())
                .description(SpeciaiizationStatusEnum.PENDING.getValue())
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

        Mockito.when(this.specializationEntityMapper.toSpecializationEntity(specializationDomainObject)).thenReturn(toCreateSpecializationEntity);
        Mockito.when(this.specializationSituationRepository
                .findByDescriptionAndActive(SpeciaiizationStatusEnum.PENDING.getValue(), true)).thenReturn(savedSpecializationStatusEntity);

        Mockito.when(this.userRepository.findByIdAndActive(userId, true)).thenReturn(Optional.of(savedUserEntity));

        toCreateSpecializationEntity.setActive(true);
        toCreateSpecializationEntity.setUser(savedUserEntity);
        toCreateSpecializationEntity.setSpecializationStatus(savedSpecializationStatusEntity);


        SpecializationEntity savedCreateSpecializationEntity = SpecializationEntity
                .builder()
                .id(UUID.randomUUID())
                .user(savedUserEntity)
                .area(specializationDomainObject.area())
                .type(specializationDomainObject.type())
                .courseLoad(specializationDomainObject.courseLoad())
                .totalCost(specializationDomainObject.totalCost())
                .specializationStatus(savedSpecializationStatusEntity)
                .active(true)
                .build();

        Mockito.when(this.specializationRepository.save(toCreateSpecializationEntity)).thenReturn(savedCreateSpecializationEntity);
        Mockito.when(this.specializationEntityMapper.toSpecializationDomainObject(savedCreateSpecializationEntity)).thenReturn(specializationDomainObject);


        Specialization result = this.specializationGateway.create(specializationDomainObject);

        Assertions.assertThat(result).isEqualTo(specializationDomainObject);

        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationEntity(specializationDomainObject);
        Mockito.verify(this.specializationSituationRepository, Mockito.times(1)).findByDescriptionAndActive(SpeciaiizationStatusEnum.PENDING.getValue(), true);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdAndActive(userId, true);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).save(toCreateSpecializationEntity);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationDomainObject(savedCreateSpecializationEntity);
    }

    @Test
    @DisplayName("Should list of SpecializationAndUser ")
    void shouldListOfSpecializationAndUser() {
        List<SpecializationEntity> specializationEntityList = new ArrayList<>
                (Arrays.asList(Mocks.specializationEntityFactory(), Mocks.specializationEntityFactory()));

        List<SpecializationAndUser> specializationAndUserList = new ArrayList<>
                (Arrays.asList(Mocks.specializationAndUserFactory(specializationEntityList.getFirst()), Mocks.specializationAndUserFactory(specializationEntityList.get(1))));

        Mockito.when(this.specializationRepository.findAll()).thenReturn(specializationEntityList);
        Mockito.when(this.specializationEntityMapper.toSpecAndUserListDomainObjects(specializationEntityList)).thenReturn(specializationAndUserList);

        List<SpecializationAndUser> result = this.specializationGateway.getAll();

        Assertions.assertThat(result).isEqualTo(specializationAndUserList);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findAll();
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecAndUserListDomainObjects(specializationEntityList);
    }

    @Test
    @DisplayName("should return null if the specialization does not exists")
    void ShouldReturnNullIfTheSpecializationDoesNotExists() {
        UUID specializationId = UUID.randomUUID();
        Mockito.when(this.specializationRepository.findByIdAndActive(specializationId, true)).thenReturn(Optional.empty());
        Specialization result = this.specializationGateway.findById(specializationId);
        Assertions.assertThat(result).isNull();
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findByIdAndActive(specializationId, true);
    }

    @Test
    @DisplayName("Should return specialization domain object")
    void shouldReturnSpecializationDomainObjectById() {
        SpecializationEntity savedSpecializationEntity = Mocks.specializationEntityFactory();
        Specialization specializationObjectDomain = Mocks.specializationDomainObjectFactory(savedSpecializationEntity);

        Mockito.when(this.specializationRepository.findByIdAndActive(savedSpecializationEntity.getId(), true)).thenReturn(Optional.of(savedSpecializationEntity));
        Mockito.when(this.specializationEntityMapper.toSpecializationDomainObject(savedSpecializationEntity)).thenReturn(specializationObjectDomain);

        Specialization result = this.specializationGateway.findById(savedSpecializationEntity.getId());

        Assertions.assertThat(result).isEqualTo(specializationObjectDomain);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findByIdAndActive(savedSpecializationEntity.getId(), true);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationDomainObject(savedSpecializationEntity);
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
        SpecializationEntity specializationEntity = Mocks.specializationEntityFactory();
        SpecializationStatusEntity specializationStatusEntity = Mocks.specializationStatusEntityFactory();
        specializationStatusEntity.setDescription("APPROVED");

        Mockito.when(this.specializationRepository.findByIdAndActive(specializationEntity.getId(), true)).thenReturn(Optional.of(specializationEntity));
        Mockito.when(this.specializationSituationRepository.findByDescriptionAndActive(SpeciaiizationStatusEnum.APPROVED.getValue(), true)).thenReturn(specializationStatusEntity);

        specializationEntity.setSpecializationStatus(specializationStatusEntity);
        Mockito.when(this.specializationRepository.save(specializationEntity)).thenReturn(specializationEntity);

        SpecializationAndUser specializationAndUser = Mocks.specializationAndUserFactory(specializationEntity);
        Mockito.when(this.specializationEntityMapper.toSpecAndUserDomainObject(specializationEntity)).thenReturn(specializationAndUser);

        SpecializationAndUser result = this.specializationGateway.approve(specializationEntity.getId());

        Assertions.assertThat(result).isEqualTo(specializationAndUser);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).findByIdAndActive(specializationEntity.getId(), true);
        Mockito.verify(this.specializationSituationRepository, Mockito.times(1)).findByDescriptionAndActive(SpeciaiizationStatusEnum.APPROVED.getValue(), true);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).save(specializationEntity);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecAndUserDomainObject(specializationEntity);
    }

}
