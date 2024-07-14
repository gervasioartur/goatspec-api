package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.domain.Enums.SpeciaiizationSituationEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
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
                (specializationSituationRepository, specializationRepository, specializationEntityMapper, userRepository);
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
                .description(SpeciaiizationSituationEnum.PENDING.getValue())
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
                .findByDescriptionAndActive(SpeciaiizationSituationEnum.PENDING.getValue(), true)).thenReturn(savedSpecializationStatusEntity);

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
        Mockito.when(this.specializationEntityMapper.toDomainObject(savedCreateSpecializationEntity)).thenReturn(specializationDomainObject);


        Specialization result = this.specializationGateway.create(specializationDomainObject);

        Assertions.assertThat(result).isEqualTo(specializationDomainObject);

        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toSpecializationEntity(specializationDomainObject);
        Mockito.verify(this.specializationSituationRepository, Mockito.times(1)).findByDescriptionAndActive(SpeciaiizationSituationEnum.PENDING.getValue(), true);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdAndActive(userId, true);
        Mockito.verify(this.specializationRepository, Mockito.times(1)).save(toCreateSpecializationEntity);
        Mockito.verify(this.specializationEntityMapper, Mockito.times(1)).toDomainObject(savedCreateSpecializationEntity);
    }

    @Test
    @DisplayName("Should list of SpecializationAndUser ")
    void shouldListOfSpecializationAndUser() {
        List<SpecializationEntity> specializationEntityList = new ArrayList<>
                (Arrays.asList(Mocks.specializationEntityFactory(),Mocks.specializationEntityFactory()));

        List<SpecializationAndUser> specializationAndUserList = new ArrayList<>
                (Arrays.asList(Mocks.specializationAndUserFactory(specializationEntityList.getFirst()),Mocks.specializationAndUserFactory(specializationEntityList.get(1))));

        Mockito.when(this.specializationRepository.findAll()).thenReturn(specializationEntityList);
        Mockito.when(this.specializationEntityMapper.toDomainObjects(specializationEntityList)).thenReturn(specializationAndUserList);

       List<SpecializationAndUser> result =  this.specializationGateway.getAll();

       Assertions.assertThat(result).isEqualTo(specializationAndUserList);
       Mockito.verify(this.specializationRepository, Mockito.times(1)).findAll();
       Mockito.verify(this.specializationEntityMapper,Mockito.times(1)).toDomainObjects(specializationEntityList);
    }
}
